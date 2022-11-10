package com.example.debit72.android.presenter.service.registry_ip

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
import model.IP

class RegistryIPStoreFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistryIPStore(context) as T
    }
}

class RegistryIPStore(context: Context) :
    ReduxStoreViewModel<RegistryIPStore.State, RegistryIPStore.Action, RegistryIPStore.Effect>() {

    sealed interface State : ReduxStore.State {
        object Loading : State
        data class LoadingError(val error: String?) : State
        data class Data(
            val ip: List<IP>,
            val query: String?
        ) : State
    }

    sealed interface Action : ReduxStore.Action {
        data class Search(
            val query: String
        ) : Action

        object ClearIP : Action
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
            Action.ClearIP -> State.Data(ip = emptyList(), null)
        }
    }

    private fun loading(action: Action.Search): State {
        launch(Dispatchers.IO) {
            runCatching {
                val ip =
                    sdk.selectIpFromString(action.query)
                dispatch(
                    if (ip.isEmpty())
                        Action.Loaded(State.LoadingError("Ничего не нашлось"))
                    else
                        Action.Loaded(
                            State.Data(
                                ip,
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
