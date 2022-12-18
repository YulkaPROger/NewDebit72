package com.example.debit72.android.presenter.more

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debit72.SpaceXSDK
import com.example.debit72.android.data_store.UserSettings
import com.example.debit72.android.presenter.store.ReduxStore
import com.example.debit72.android.presenter.store.ReduxStoreViewModel
import com.example.debit72.android.utils.getCurrentDateTime
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
            val loadingIP: Boolean = false,
            val loadingSpr: Boolean = false,
            val loadingAuto: Boolean = false,
            val loadingArrestedAuto: Boolean = false,
            val loadingArrestedProperty: Boolean = false,
            val updateAll: Boolean = false
        ) : State

        data class LoadingError(val error: Throwable) : State
        object Data : State
    }

    sealed interface Action : ReduxStore.Action {
        object Start : Action
        data class Loaded(val result: State) : Action
        object UpdateIP : Action
        object UpdateSpr : Action
        object UpdateAuto : Action
        object UpdateArrestedAuto : Action
        object UpdateArrestedProperty : Action
        object UpdateAll : Action
        data class SetApiKey(val key: String) : Action
    }

    sealed interface Effect : ReduxStore.Effect {
        data class ShowError(val message: Throwable) : Effect
    }

    override val logTag: String = "MoreStore"


    override fun doAction(action: Action, oldState: State?): State {
        return when (action) {
            is Action.Start -> loading()
            is Action.Loaded -> {
                action.result
            }
            Action.UpdateIP -> updateIP(oldState)
            Action.UpdateSpr -> updateSpr(oldState)
            Action.UpdateAuto -> updateAuto(oldState)
            Action.UpdateArrestedAuto -> updateArrestedAuto(oldState)
            Action.UpdateArrestedProperty -> updateArrestedProperty(oldState)
            Action.UpdateAll -> updateAll(oldState)
            is Action.SetApiKey -> setApiKey(action)
        }
    }

    private fun setApiKey(action: Action.SetApiKey): State {
        launch(Dispatchers.IO) {
            runCatching {
                dataStore.setString(action.key, UserSettings.API_KEY)
            }.onSuccess {
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

    private fun updateIP(oldState: State?): State {
        launch(Dispatchers.IO) {
            runCatching {
                sdk.updateIP()
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
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }
        }
        return if (oldState is State.Loading) {
            oldState.copy(
                loadingIP = true
            )
        } else State.Loading(loadingIP = true)
    }

    private fun updateSpr(oldState: State?): State {
        launch(Dispatchers.IO) {
            runCatching {
                sdk.updateSpr()
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it)))
                Log.e("MoreStore", "loading", it)
            }.onSuccess {
                val count = sdk.selectCountSpr()
                dataStore.setString(count.toString(), UserSettings.COUNT_SPR)
                val time = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
                dataStore.setString(time, UserSettings.DATE_UPDATE_SPR)
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }
        }
        return if (oldState is State.Loading) {
            oldState.copy(
                loadingSpr = true
            )
        } else State.Loading(loadingSpr = true)
    }

    private fun updateAuto(oldState: State?): State {
        launch(Dispatchers.IO) {
            runCatching {
                sdk.updateAuto()
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it)))
                Log.e("MoreStore", "loading", it)
            }.onSuccess {
                val count = sdk.selectCountAuto()
                dataStore.setString(count.toString(), UserSettings.COUNT_AUTO)
                val time = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
                dataStore.setString(time, UserSettings.DATE_UPDATE_AUTO)
                Log.d("date and count", time + count)
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }
        }
        return if (oldState is State.Loading) {
            oldState.copy(
                loadingAuto = true
            )
        } else State.Loading(loadingAuto = true)
    }

    private fun updateArrestedAuto(oldState: State?): State {
        launch(Dispatchers.IO) {
            runCatching {
                sdk.updateArrestedAuto()
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it)))
                Log.e("MoreStore", "loading", it)
            }.onSuccess {
                val count = sdk.selectCountArrestedAuto()
                dataStore.setString(count.toString(), UserSettings.COUNT_ARRESTED_AUTO)
                val time = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
                dataStore.setString(time, UserSettings.DATE_UPDATE_ARRESTED_AUTO)
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }
        }
        return if (oldState is State.Loading) {
            oldState.copy(
                loadingArrestedAuto = true
            )
        } else State.Loading(loadingArrestedAuto = true)
    }

    private fun updateArrestedProperty(oldState: State?): State {
        launch(Dispatchers.IO) {
            runCatching {
                sdk.updateArrestedProperty()
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it)))
                Log.e("MoreStore", "loading", it)
            }.onSuccess {
                val count = sdk.selectCountArrestedProperty()
                dataStore.setString(count.toString(), UserSettings.COUNT_ARRESTED_PROPERTY)
                val time = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
                dataStore.setString(time, UserSettings.DATE_UPDATE_ARRESTED_PROPERTY)
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }
        }
        return if (oldState is State.Loading) {
            oldState.copy(
                loadingArrestedProperty = true
            )
        } else State.Loading(loadingArrestedProperty = true)
    }

    private fun updateAll(oldState: State?): State {
        launch(Dispatchers.IO) {
//            runCatching {
//                sdk.updateArrestedAuto()
//            }.onSuccess {
//                //Arrested auto
//                val countArrestedAuto = sdk.selectCountArrestedAuto()
//                dataStore.setString(countArrestedAuto.toString(), UserSettings.COUNT_ARRESTED_AUTO)
//                val timeArrestedAuto = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
//                dataStore.setString(timeArrestedAuto, UserSettings.DATE_UPDATE_ARRESTED_AUTO)
//            }.onFailure {
//                dispatch(Action.Loaded(State.LoadingError(it)))
//            }
            runCatching {
                sdk.updateAuto()
            }.onSuccess {
                // auto
                val countAuto = sdk.selectCountAuto()
                dataStore.setString(countAuto.toString(), UserSettings.COUNT_AUTO)
                val timeAuto = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
                dataStore.setString(timeAuto, UserSettings.DATE_UPDATE_AUTO)
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it)))
            }
            runCatching {
                sdk.updateSpr()
            }.onSuccess {
                // SPR
                val countSPR = sdk.selectCountSpr()
                dataStore.setString(countSPR.toString(), UserSettings.COUNT_SPR)
                val timeSPR = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
                dataStore.setString(timeSPR, UserSettings.DATE_UPDATE_SPR)
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it)))
            }
//            runCatching {
//                sdk.updateArrestedProperty()
//            }.onSuccess {
//                //Arrested auto
//                val countArrestedProperty = sdk.selectCountArrestedProperty()
//                dataStore.setString(countArrestedProperty.toString(), UserSettings.COUNT_ARRESTED_PROPERTY)
//                val timeArrestedProperty = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
//                dataStore.setString(timeArrestedProperty, UserSettings.DATE_UPDATE_ARRESTED_PROPERTY)
//            }.onFailure {
//                dispatch(Action.Loaded(State.LoadingError(it)))
//            }
            runCatching {
                sdk.updateIP()
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it)))
                Log.e("MoreStore", "loading", it)
            }.onSuccess {
                //IP
                val countIP = sdk.selectCount()
                dataStore.setString(countIP.toString(), UserSettings.COUNT_IP)
                val timeIP = getCurrentDateTime().toNormalDate("dd.MM.yyyy")
                dataStore.setString(timeIP, UserSettings.DATE_UPDATE_IP)
                dispatch(
                    Action.Loaded(
                        State.Data
                    )
                )
            }
        }
        return if (oldState is State.Loading) {
            oldState.copy(
                updateAll = true
            )
        } else State.Loading(updateAll = true)
    }


    private fun loading(): State {
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
