package com.example.debit72.android.presenter.service.auto

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


class AutoStoreFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AutoStore(context) as T
    }
}


class AutoStore(context: Context) :
    ReduxStoreViewModel<AutoStore.State, AutoStore.Action, AutoStore.Effect>() {

    sealed interface State : ReduxStore.State {
        object Loading : State
        data class LoadingError(val error: String?) : State
        data class Data(
            val auto: List<PresenterAuto>,
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
    override val logTag: String = "AutoStore"

    override fun doAction(action: Action, oldState: State?): State? {
        return when (action) {
            is Action.Search -> loading(action)
            is Action.Loaded -> {
                action.result
            }
            Action.ClearIP -> State.Data(auto = emptyList(), null)
        }
    }

    private fun loading(action: Action.Search): State {
        launch(Dispatchers.IO) {
            runCatching {
                val auto = sdk.selectAutoFromString(action.query)

                val presenterAuto: List<PresenterAuto> = auto.map { autoInBd ->
                    val listIp: List<PresenterIP?> =
                        autoInBd.ipClaimant.split(";")
                            .map { numberIp ->
                                if (numberIp.isNotBlank()) {
                                    val numberWithoutSpace = numberIp.replace("\\s".toRegex(), "")
                                    val ip = sdk.selectIpFromNumber(numberWithoutSpace.toInt())
                                    PresenterIP(
                                        regNumberIP = ip.regNumberIP,
                                        totalAmountDebt = ip.totalAmountDebt,
                                        claimant = ip.claimant,
                                        address = ip.address,
                                        balanceOwed = ip.balanceOwed,
                                        number = numberWithoutSpace.toInt()
                                    )
                                } else null
                            }
                    PresenterAuto(
                        owner = autoInBd.owner,
                        modelTs = autoInBd.modelTs,
                        ipClaimant = autoInBd.ipClaimant,
                        gosNumber = autoInBd.gosNumber,
                        listIp = listIp
                    )
                }
                Log.d("AutoStore presenterAuto", presenterAuto.toString())
                dispatch(
                    if (auto.isEmpty())
                        Action.Loaded(State.LoadingError("В БД ничего не нашлось"))
                    else Action.Loaded(
                        State.Data(
                            auto = presenterAuto,
                            query = action.query
                        )
                    )
                )
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it.message)))
                Log.e("AutoStore", "loading", it)
            }
        }
        return State.Loading
    }
}

data class PresenterAuto(
    val owner: String,
    val gosNumber: String,
    val modelTs: String,
    val ipClaimant: String,
    val listIp: List<PresenterIP?> = emptyList()
)

data class PresenterIP(
    val regNumberIP: String,
    val claimant: String,
    val address: String,
    val totalAmountDebt: String,
    val balanceOwed: String,
    val number: Int
)
