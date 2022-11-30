package com.example.debit72.android.presenter.service.arrested_auto

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debit72.SpaceXSDK
import com.example.debit72.android.presenter.service.auto.PresenterIP
import com.example.debit72.android.presenter.store.ReduxStore
import com.example.debit72.android.presenter.store.ReduxStoreViewModel
import com.example.debit72.database.DatabaseDriverFactory
import com.example.debit72.repository.ArrestedAutoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.OldArrestedAuto


class ArrestedAutoStoreFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArrestedAutoStore(context) as T
    }
}


class ArrestedAutoStore(context: Context) :
    ReduxStoreViewModel<ArrestedAutoStore.State, ArrestedAutoStore.Action, ArrestedAutoStore.Effect>() {

    sealed interface State : ReduxStore.State {
        object Loading : State
        data class LoadingError(val error: String?) : State
        data class Data(
            val auto: List<OldArrestedAuto>,
            val query: String?
        ) : State {
            val filterAuto: List<OldArrestedAuto> = auto.filter {
                if (!query.isNullOrBlank()) {
                    val adc = it.ip.filter { oldIP ->
                        oldIP.claimant.contains(query, true) ||
                                oldIP.regNumberIp.contains(query, true) ||
                                oldIP.rosp.contains(query, true) ||
                                oldIP.spi.contains(query, true)
                    }
                    it.modelTs.contains(query, true) ||
                            it.gosNumber.contains(query, true) ||
                            it.owner.contains(query, true) || adc.isNotEmpty()


                } else true
            }
        }
    }

    sealed interface Action : ReduxStore.Action {
        data class Search(
            val query: String
        ) : Action

        object Init : Action
        object Clear : Action
        data class Loaded(val result: State) : Action
    }

    sealed interface Effect : ReduxStore.Effect {
        data class ShowError(val message: Throwable) : Effect
    }

    val repo = ArrestedAutoRepository()
    val sdk = SpaceXSDK(DatabaseDriverFactory(context))
    override val logTag: String = "ArrestedAutoStore"

    override fun doAction(action: Action, oldState: State?): State {
        return when (action) {
            is Action.Search -> searching(oldState, action)
            is Action.Loaded -> {
                action.result
            }
            Action.Clear -> clear(oldState)
            Action.Init -> loading()
        }
    }

    init {
        dispatch(Action.Init)
    }

    private fun clear(oldState: State?): State {
        if (oldState is State.Data) {
            dispatch(
                Action.Loaded(
                    State.Data(
                        auto = oldState.auto,
                        query = null
                    )
                )
            )
        }
        return State.Loading
    }

    private fun searching(oldState: State?, action: Action.Search): State {
        launch(Dispatchers.IO) {
            runCatching {
                if (oldState is State.Data) {
                    dispatch(
                        Action.Loaded(
                            State.Data(
                                auto = oldState.auto,
                                query = action.query
                            )
                        )
                    )
                }
            }.onFailure {
                dispatch(Action.Loaded(State.LoadingError(it.message)))
                Log.e("AutoStore", "loading", it)
            }
        }
        return State.Loading
    }

    private fun loading(): State {
        launch(Dispatchers.IO) {
            runCatching {
                val auto = repo.getOldArrestedAutoList()
                Log.d("AutoStore presenterAuto", auto.toString())
                dispatch(
                    if (auto.isEmpty())
                        Action.Loaded(State.LoadingError("Ошибка загрузки данных"))
                    else Action.Loaded(
                        State.Data(
                            auto = auto,
                            query = ""
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

data class PresenterArrestedAuto(
    val modelTs: String,
    val gosNumber: String,
    val dateArrestedTs: String,
    val repository: String,
    val price: String,
    val owner: String,
    val listIp: List<PresenterIP?> = emptyList(),
    val searchLine: String
)

//TODO вернуться когда необходимо будет хранить это в БД

//                val auto = sdk.selectAllArrestedAuto()
//                val presenterAuto: List<PresenterArrestedAuto> = auto.map { autoInBd ->
//                    val listIp: List<PresenterIP?> =
//                        autoInBd.ipDebtor.split(";")
//                            .map { numberIp ->
//                                if (numberIp.isNotBlank()) {
//                                    val numberWithoutSpace = numberIp.replace("\\s".toRegex(), "")
//                                    val ip = sdk.selectIpFromNumber(numberWithoutSpace.toInt())
//                                    PresenterIP(
//                                        regNumberIP = ip.regNumberIP,
//                                        totalAmountDebt = ip.totalAmountDebt,
//                                        claimant = ip.claimant,
//                                        address = ip.address,
//                                        balanceOwed = ip.balanceOwed,
//                                        number = numberWithoutSpace.toInt(),
//                                        spi = ip.spi,
//                                        rosp = ip.rosp
//                                    )
//                                } else null
//                            }
//                    PresenterArrestedAuto(
//                        modelTs = autoInBd.modelTs,
//                        gosNumber = autoInBd.gosNumber,
//                        dateArrestedTs = autoInBd.dateArrestedTs,
//                        repository = autoInBd.repository,
//                        price = autoInBd.price,
//                        owner = autoInBd.owner,
//                        listIp = listIp,
//                        searchLine = autoInBd.searchLine
//                    )
//                }