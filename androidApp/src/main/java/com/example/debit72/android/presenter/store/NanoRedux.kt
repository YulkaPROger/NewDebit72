package com.example.debit72.android.presenter.store

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class ReduxStoreViewModel<S : ReduxStore.State, A : ReduxStore.Action, E : ReduxStore.Effect> :
    ViewModel(), ReduxStore<S, A, E>, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    protected abstract val logTag: String

    private val _state = MutableStateFlow<S?>(null)
    private val _sideEffect = Channel<E>(Channel.BUFFERED)
    override fun observeState(): StateFlow<S?> = _state
    override fun observeSideEffect(): Flow<E> = _sideEffect.receiveAsFlow().onEach {
    }

    private val actions = Channel<A>(Channel.BUFFERED)

    init {
        launch {
            actions.receiveAsFlow().collect {
                dispatchAction(it)
            }
        }
    }

    override fun dispatch(action: A) {
        actions.trySend(action)
    }

    private fun dispatchAction(action: A) {
        Log.d(logTag, "start $action")
        val oldState = _state.value

        runCatching {
            val newState = doAction(action, oldState)

            if (newState != oldState) {
                Log.d(logTag, "end newState: $newState")
                _state.value = newState
            }
        }.onFailure {
            Log.e(logTag, "dispatchError", it)
        }
    }

    abstract fun doAction(action: A, oldState: S?): S?

    protected fun sendEffect(e: E) {
        _sideEffect.trySend(e)
    }

    fun reset() {
        _state.tryEmit(null)
    }
}


abstract class ReduxStoreImpl<S : ReduxStore.State, A : ReduxStore.Action, E : ReduxStore.Effect> :
    ReduxStore<S, A, E>, CoroutineScope by CoroutineScope(Dispatchers.Main) {
    abstract override fun observeState(): StateFlow<S>
    abstract override fun observeSideEffect(): Flow<E>
    protected abstract fun dispatchAction(action: A)

    private val actions = Channel<A>(Channel.BUFFERED)

    init {
        launch {
            actions.receiveAsFlow().collect {
                dispatchAction(it)
            }
        }
    }

    override fun dispatch(action: A) {
        actions.trySend(action)
    }
}

interface ReduxStore<S : ReduxStore.State, A : ReduxStore.Action, E : ReduxStore.Effect> {
    fun observeState(): StateFlow<S?>
    fun observeSideEffect(): Flow<E>
    fun dispatch(action: A)

    interface State
    interface Action
    interface Effect
}

interface StoreHolder<S : ReduxStore.State, A : ReduxStore.Action> {
    val state: StateFlow<S?>
    fun dispatch(action: A)
}

class StoreHolderImpl<S : ReduxStore.State, A : ReduxStore.Action, E : ReduxStore.Effect>(
    private val store: ReduxStore<S, A, E>,
    effectListener: ((effect: E) -> Unit)? = null
) : StoreHolder<S, A>, CoroutineScope by CoroutineScope(Dispatchers.Main) {
    override val state = store.observeState()
    override fun dispatch(action: A) {
        store.dispatch(action)
    }

    init {
        launch {
            store.observeSideEffect().collect {
                effectListener?.invoke(it)
            }
        }
    }
}

@Composable
fun <S : ReduxStore.State, A : ReduxStore.Action, E : ReduxStore.Effect> rememberStoreStateHolder(
    store: ReduxStore<S, A, E>,
    effectListener: ((effect: E) -> Unit)? = null
): StoreHolder<S, A> = remember(store, effectListener) {
    StoreHolderImpl(store, effectListener)
}

fun <S : ReduxStore.State, A : ReduxStore.Action> mockStoreHolder(state: S? = null) =
    object : StoreHolder<S, A> {
        override val state: StateFlow<S?> = MutableStateFlow(state)
        override fun dispatch(action: A) {}
    }


/** Вызывает лямбду для дочернего типа, или возвращает исходный класс
 *
 * Если лябда возвращает null, то возвращает исходный класс
 *
 * *state.updateAs { state: State.Data -> state.copy(...) }*
 *
 * *state.updateAs { state: State.Data -> if (state.aaa) state.copy(...) else null }*
 */
inline fun <T : ReduxStore.State?, reified S : T> T.updateAs(update: (state: S) -> T?): T {
    return (this as? S)?.let { update(this) } ?: this
}
