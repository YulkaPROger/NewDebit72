package com.example.debit72.android.presenter.service.auto_from_number

import android.util.Log
import com.example.debit72.android.presenter.store.ReduxStore
import com.example.debit72.android.presenter.store.ReduxStoreViewModel
import com.example.debit72.repository.AutoNumberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.AutoFromNumber

class AutoFromNumberStore :
    ReduxStoreViewModel<AutoFromNumberStore.State, AutoFromNumberStore.Action, AutoFromNumberStore.Effect>() {

    sealed interface State : ReduxStore.State {
        object Loading : State
        data class LoadingError(val error: String) : State
        data class Data(
            var autoList: List<AutoFromNumber>,
            val query: String
        ) : State
    }

    sealed interface Action : ReduxStore.Action {
        data class Search(
           val query: String
        ) : Action

        data class Loaded(val result: State) : Action

        object Clear: Action
    }

    sealed interface Effect : ReduxStore.Effect {
        data class ShowError(val message: Throwable) : Effect
    }

    override val logTag: String = "AutoFromNumberStore"

    override fun doAction(action: Action, oldState: State?): State? {
        return when (action) {
            is Action.Search -> search(action)
            is Action.Loaded -> {
                action.result
            }
            Action.Clear -> clearState()
        }
    }

    private fun clearState(): State {
        launch(Dispatchers.IO) {
            runCatching {
                dispatch(
                    Action.Loaded(
                        State.Data(
                            autoList = emptyList(),
                            query = ""
                        )
                    ))
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it.message.toString())))
                Log.e("AutoFromNumberStore", "loading", it)
            }
        }
        return State.Loading
    }

    private fun search(action: Action.Search): State {
        launch(Dispatchers.IO) {
            runCatching {
                AutoNumberRepository().getAuto(action.query)
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it.message.toString())))
                Log.e("AutoFromNumberStore", "loading", it)
            }.onSuccess {
                dispatch(
                    Action.Loaded(
                        State.Data(
                            autoList = it,
                            query = action.query
                    )
                ))
            }
        }
        return State.Loading
    }
}
