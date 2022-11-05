package com.example.debit72.android.presenter.more

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debit72.SpaceXSDK
import com.example.debit72.android.data_store.UserSettings
import com.example.debit72.android.utils.getCurrentDateTime
import com.example.debit72.android.presenter.store.ReduxStore
import com.example.debit72.android.presenter.store.ReduxStoreViewModel
import com.example.debit72.android.utils.toNormalDate
import com.example.debit72.database.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoreStoreFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoreStore(context) as T
    }
}

class MoreStore(context: Context) :
    ReduxStoreViewModel<MoreStore.State, MoreStore.Action, MoreStore.Effect>() {

    val dataStore = UserSettings(context)
    val sdk = SpaceXSDK(DatabaseDriverFactory(context))

    sealed interface State : ReduxStore.State {
        data class Loading(
            val loadingIP: Boolean = false
        ) : State

        data class LoadingError(val error: Throwable) : State
        object Data : State
    }

    sealed interface Action : ReduxStore.Action {
        object Start : Action
        data class Loaded(val result: State) : Action
        object UpdateIP : Action
    }

    sealed interface Effect : ReduxStore.Effect {
        data class ShowError(val message: Throwable) : Effect
    }

    override val logTag: String = "MoreStore"


    override fun doAction(action: Action, oldState: State?): State {
        return when (action) {
            is Action.Start -> loading(action)
            is Action.Loaded -> {
                action.result
            }
            Action.UpdateIP -> updateIP()
        }
    }

    private fun updateIP(): State {
        launch(Dispatchers.IO) {
            runCatching {
                sdk.updateIP(true)
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it)))
                Log.e("MoreStore", "loading", it)
            }.onSuccess {
                val count = sdk.selectCount()
                dataStore.setString(count.toString(), UserSettings.COUNT_IP)
                val time = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
                dataStore.setString(time, UserSettings.DATE_UPDATE_IP)
                Log.d("date and count", time + count)
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }
        }
        return State.Loading(loadingIP = true)
    }

    private fun loading(action: Action.Start): State {
        launch(Dispatchers.IO) {
            runCatching {
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it)))
                Log.e("MoreStore", "loading", it)
            }
        }
        return State.Loading()
    }
}
