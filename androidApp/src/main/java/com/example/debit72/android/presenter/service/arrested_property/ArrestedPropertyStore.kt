package com.example.debit72.android.presenter.service.arrested_property

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debit72.SpaceXSDK
import com.example.debit72.android.presenter.service.auto.PresenterIP
import com.example.debit72.android.presenter.store.ReduxStore
import com.example.debit72.android.presenter.store.ReduxStoreViewModel
import com.example.debit72.database.DatabaseDriverFactory
import com.example.debit72.repository.ArrestedPropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.OldArrestedProperty


class ArrestedPropertyStoreFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArrestedPropertyStore(context) as T
    }
}

class ArrestedPropertyStore(context: Context) :
    ReduxStoreViewModel<ArrestedPropertyStore.State, ArrestedPropertyStore.Action, ArrestedPropertyStore.Effect>() {

    sealed interface State : ReduxStore.State {
        object Loading : State
        data class LoadingError(val error: String?) : State
        data class Data(
            val properties: List<OldArrestedProperty>,
            val query: String?,
        ) : State {
            val filterProperties: List<OldArrestedProperty> = properties.filter {
                if (!query.isNullOrBlank()) {
                    val adc = it.ip.filter { oldIP ->
                        oldIP.claimant.contains(query, true) ||
                                oldIP.regNumberIp.contains(query, true) ||
                                oldIP.rosp.contains(query, true) ||
                                oldIP.spi.contains(query, true)
                    }

                    it.owner.contains(query, true) ||
                            it.dateArrested.contains(query, true) ||
                            it.property.contains(query, true) ||
                            it.price.contains(query, true) || adc.isNotEmpty()
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

    val repo = ArrestedPropertyRepository()
    override val logTag: String = "ArrestedPropertyStore"

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
                        properties = oldState.properties,
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
                                properties = oldState.properties,
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
                val property = repo.getOldArrestedAutoList()


                Log.d("AutoStore presenterAuto", property.toString())
                dispatch(
                    if (property.isEmpty())
                        Action.Loaded(State.LoadingError("Ошибка загрузки данных"))
                    else Action.Loaded(
                        State.Data(
                            properties = property,
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

data class PresenterArrestedProperty(
    val propertyDebtor: String,
    val sum: String,
    val dateArrested: String,
    val debtor: String,
    val ipClaimant: List<PresenterIP?> = emptyList(),
    val searchLine: String
)

//TODO вернуться к этому когда нужно будет хранение в БД

//val properties = sdk.selectAllArrestedProperty()

//val presenterAuto: List<PresenterArrestedProperty> =
//    properties.map { arrestedProperty ->
//        val listIp: List<PresenterIP?> =
//            arrestedProperty.ipClaimant.split(";")
//                .map { numberIp ->
//                    if (numberIp.isNotBlank()) {
//                        val numberWithoutSpace =
//                            numberIp.replace("\\s".toRegex(), "")
//                        val ip = sdk.selectIpFromNumber(numberWithoutSpace.toInt())
//                        PresenterIP(
//                            regNumberIP = ip.regNumberIP,
//                            totalAmountDebt = ip.totalAmountDebt,
//                            claimant = ip.claimant,
//                            address = ip.address,
//                            balanceOwed = ip.balanceOwed,
//                            number = numberWithoutSpace.toInt(),
//                            spi = ip.spi,
//                            rosp = ip.rosp
//                        )
//                    } else null
//                }
//        PresenterArrestedProperty(
//            propertyDebtor = arrestedProperty.propertyDebtor,
//            sum = arrestedProperty.sum,
//            dateArrested = arrestedProperty.dateArrested,
//            debtor = arrestedProperty.debtor,
//            ipClaimant = listIp,
//            searchLine = arrestedProperty.searchLine
//        )
//    }