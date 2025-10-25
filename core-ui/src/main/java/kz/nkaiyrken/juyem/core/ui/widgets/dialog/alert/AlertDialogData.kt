package kz.nkaiyrken.juyem.core.ui.widgets.dialog.alert

/**
 * Data class for alert dialog content.
 *
 * @param title Dialog title (optional)
 * @param description Dialog message text
 * @param positiveButtonText Text for positive/confirm button (optional)
 * @param negativeButtonText Text for negative/cancel button (optional)
 */
data class AlertDialogData(
    val title: String? = null,
    val description: String,
    val positiveButtonText: String? = null,
    val negativeButtonText: String? = null,
)
