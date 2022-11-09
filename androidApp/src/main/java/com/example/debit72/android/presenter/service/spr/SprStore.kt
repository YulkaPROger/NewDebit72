package com.example.debit72.android.presenter.service.spr

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debit72.SpaceXSDK
import com.example.debit72.android.presenter.store.ReduxStore
import com.example.debit72.android.presenter.store.ReduxStoreViewModel
import com.example.debit72.database.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Spr

class SprStoreFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SprStore(context) as T
    }
}

class SprStore(context: Context) :
    ReduxStoreViewModel<SprStore.State, SprStore.Action, SprStore.Effect>() {

    sealed interface State : ReduxStore.State {
        object Loading : State
        data class LoadingError(val error: String?) : State
        data class Data(
            val spr: List<Spr>,
            val query: String?
        ) : State
    }

    sealed interface Action : ReduxStore.Action {
        data class Search(
            val query: String
        ) : Action

        object ClearSpr : Action
        data class Loaded(val result: State) : Action
    }

    sealed interface Effect : ReduxStore.Effect {
        data class ShowError(val message: Throwable) : Effect
    }

    val sdk = SpaceXSDK(DatabaseDriverFactory(context))

    override val logTag: String = "RegistryIPStore"

    override fun doAction(action: Action, oldState: State?): State? {
        return when (action) {
            is Action.Search -> loading(action)
            is Action.Loaded -> {
                action.result
            }
            Action.ClearSpr -> State.Data(spr = emptyList(), null)
        }
    }

    private fun loading(action: Action.Search): State {
        launch(Dispatchers.IO) {
            runCatching {
                val spr =
                    sdk.selectSprFromString(action.query)
                dispatch(
                    if (spr.isEmpty())
                        Action.Loaded(State.LoadingError("Ничего не нашлось"))
                    else
                        Action.Loaded(
                            State.Data(
                                spr,
                                action.query
                            )
                        )
                )
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it.message)))
                Log.e("RegistryIPStore", "loading", it)
            }
        }
        return State.Loading
    }
}
