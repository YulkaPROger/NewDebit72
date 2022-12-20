package com.example.debit72.android.presenter.additional_service

import android.util.Log
import com.example.debit72.android.presenter.store.ReduxStore
import com.example.debit72.android.presenter.store.ReduxStoreViewModel
import com.example.debit72.repository.ReportErrorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.ReportError

class ReportErrorStore :
    ReduxStoreViewModel<ReportErrorStore.State, ReportErrorStore.Action, ReportErrorStore.Effect>() {

    sealed interface State : ReduxStore.State {
        object Loading : State
        data class LoadingError(val error: String) : State
        data class Data(
            val reportList: List<ReportError>,
            val query: String = ""
        ) : State {
            val filtredList: List<ReportError> = if (query.isNotEmpty()) {
                reportList.filter {
                    it.claimant.contains(query, true) ||
                            it.rosp.contains(query, true) ||
                            it.spi.contains(query, true) ||
                            it.numberIP.contains(query, true) ||
                            it.debtor.contains(query, true)
                }
            } else reportList
        }
    }

    sealed interface Action : ReduxStore.Action {
        object Start : Action
        data class Search(val query: String) : Action
        data class Loaded(val result: State) : Action
    }

    sealed interface Effect : ReduxStore.Effect {
        data class ShowError(val message: Throwable) : Effect
    }

    override val logTag: String = "ReportErrorStore"

    override fun doAction(action: Action, oldState: State?): State? {
        return when (action) {
            is Action.Start -> loading()
            is Action.Loaded -> {
                action.result
            }
            is Action.Search -> search(action, oldState)
        }
    }

    private fun search(action: Action.Search, oldState: State?): State? {
        if (oldState is State.Data)
            dispatch(
                Action.Loaded(
                    oldState.copy(
                        query = action.query
                    )
                )
            )
        return State.Loading
    }

    private fun loading(): State {
        launch(Dispatchers.IO) {
            runCatching {
                ReportErrorRepository().getInfo()
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it.message.toString())))
                Log.e("ReportErrorStore", "loading", it)
            }.onSuccess {
                dispatch(
                    Action.Loaded(
                        State.Data(
                            reportList = it
                        )
                    )
                )
            }
        }
        return State.Loading
    }
}
