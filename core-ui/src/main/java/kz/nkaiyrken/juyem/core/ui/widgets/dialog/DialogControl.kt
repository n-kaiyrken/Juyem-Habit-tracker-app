package kz.nkaiyrken.juyem.core.ui.widgets.dialog

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Dialog control for managing dialog state and results.
 * Supports showing dialogs with or without waiting for a result.
 *
 * @param T Data type to pass to the dialog
 * @param R Result type returned from the dialog
 */
class DialogControl<T : Any, R : Any> {

    sealed class State<out T : Any> {
        data class Shown<T : Any>(val data: T, val forResult: Boolean) : State<T>()
        data object Hidden : State<Nothing>()
    }

    private val mutableStateFlow = MutableStateFlow<State<T>>(State.Hidden)
    private val resultChannel = Channel<R?>(Channel.RENDEZVOUS)

    val stateFlow: StateFlow<State<T>>
        get() = mutableStateFlow

    /**
     * Show dialog without waiting for result.
     */
    fun show(data: T) {
        if (isShownForResult()) {
            resultChannel.trySend(null)
        }
        mutableStateFlow.value = State.Shown(data, forResult = false)
    }

    /**
     * Show dialog and suspend until result is received.
     */
    suspend fun showForResult(data: T): R? {
        if (isShownForResult()) {
            resultChannel.trySend(null)
        }
        mutableStateFlow.value = State.Shown(data, forResult = true)
        return resultChannel.receive()
    }

    /**
     * Send result and close dialog.
     */
    fun sendResult(result: R) {
        mutableStateFlow.value = State.Hidden
        this.resultChannel.trySend(result)
    }

    /**
     * Dismiss dialog without result.
     */
    fun dismiss() {
        if (mutableStateFlow.value == State.Hidden) {
            return
        }

        val wasShownForResult = isShownForResult()
        mutableStateFlow.value = State.Hidden
        if (wasShownForResult) {
            resultChannel.trySend(null)
        }
    }

    private fun isShownForResult(): Boolean {
        return (mutableStateFlow.value as? State.Shown<T>)?.forResult == true
    }
}

/**
 * Extension for showing dialog without data (Unit).
 */
fun <R : Any> DialogControl<Unit, R>.show() = show(Unit)

/**
 * Extension for showing dialog for result without data (Unit).
 */
suspend fun <R : Any> DialogControl<Unit, R>.showForResult(): R? = showForResult(Unit)

/**
 * Extension to get data if dialog is shown.
 */
val <T : Any, R : Any> DialogControl<T, R>.dataOrNull: T?
    get() = (this.stateFlow.value as? DialogControl.State.Shown)?.data
