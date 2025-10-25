package kz.nkaiyrken.juyem.core.ui.widgets.dialog.alert

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.button.CommonPlainButton
import kz.nkaiyrken.juyem.core.ui.widgets.dialog.DialogControl
import kz.nkaiyrken.juyem.core.ui.widgets.dialog.DialogResult

/**
 * Show alert dialog using DialogControl.
 *
 * @param dialogControl Control for managing dialog state and results
 */
@Composable
fun ShowAlertDialog(dialogControl: DialogControl<AlertDialogData, DialogResult>) {
    ShowDialog(dialogControl) { data ->
        AlertDialogContent(data, dialogControl)
    }
}

@Composable
private fun <T : Any, R : Any> ShowDialog(
    dialogControl: DialogControl<T, R>,
    dialog: @Composable (data: T) -> Unit,
) {
    val state by dialogControl.stateFlow.collectAsState()
    (state as? DialogControl.State.Shown)?.data?.let { data ->
        dialog(data)
    }
}

@Composable
private fun AlertDialogContent(
    data: AlertDialogData,
    dialogControl: DialogControl<AlertDialogData, DialogResult>,
) {
    AlertDialog(
        shape = MaterialTheme.shapes.small,
        title = data.title?.let { text ->
            {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = text,
                    color = MaterialTheme.additionalColors.elementsHighContrast,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                )
            }
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.description,
                color = MaterialTheme.additionalColors.elementsLowContrast,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        confirmButton = {
            data.positiveButtonText?.let { text ->
                CommonPlainButton(
                    modifier = Modifier.padding(end = 8.dp),
                    text = text,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    onClick = { dialogControl.sendResult(DialogResult.Confirm) },
                )
            }
        },
        dismissButton = {
            data.negativeButtonText?.let { text ->
                CommonPlainButton(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    onClick = { dialogControl.sendResult(DialogResult.Cancel) },
                )
            }
        },
        onDismissRequest = { dialogControl.dismiss() },
    )
}

@Preview
@Composable
private fun AlertDialogPreview() {
    JuyemTheme {
        AlertDialogContent(
            data = AlertDialogData(
                title = "Error",
                description = "No internet connection. Please check your network settings.",
                negativeButtonText = "Close",
            ),
            dialogControl = DialogControl(),
        )
    }
}

@Preview
@Composable
private fun AlertDialogWithTwoButtonsPreview() {
    JuyemTheme {
        AlertDialogContent(
            data = AlertDialogData(
                title = "Delete Habit",
                description = "Are you sure you want to delete this habit? This action cannot be undone.",
                positiveButtonText = "Delete",
                negativeButtonText = "Cancel",
            ),
            dialogControl = DialogControl(),
        )
    }
}
