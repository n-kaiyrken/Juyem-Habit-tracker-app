package kz.nkaiyrken.juyem.features.habits.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.Gray900
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.button.CommonHabitButton
import kz.nkaiyrken.juyem.features.habits.R

@Composable
fun DefaultExpandedContent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onMarkComplete: () -> Unit = {},
    onEditNote: () -> Unit = {},
    onSkip: () -> Unit = {},
    onClear: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CommonHabitCompleteButton(
                onMarkComplete = onMarkComplete,
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
            CommonHabitClearButton(
                onCancel = onClear,
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CommonHabitNoteButton(
                onEditNote = onEditNote,
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
            CommonHabitSkipButton(
                onSkip = onSkip,
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TimerExpandedContent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onStartTimer: () -> Unit = {},
    onEditNote: () -> Unit = {},
    onSkip: () -> Unit = {},
    onMarkComplete: () -> Unit = {},
    onClear: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CommonHabitButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.start_timer),
            onClick = onStartTimer,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.additionalColors.elementsAccent,
            ),
            icon = Icons.Outlined.Timer,
            enabled = enabled
        )
        DefaultExpandedContent(
            enabled = enabled,
            onMarkComplete = onMarkComplete,
            onEditNote = onEditNote,
            onSkip = onSkip,
            onClear = onClear,
        )
    }
}

@Composable
fun CounterExpandedContent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onConfirm: (count: Int) -> Unit = {},
    onEditNote: () -> Unit = {},
    onSkip: () -> Unit = {},
) {
    var count by remember { mutableStateOf(TextFieldValue(text = "1")) }
    var hasFocus by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),

    ) {
        // Counter input and OK button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Count input field
            OutlinedTextField(
                value = count,
                prefix = { Text(stringResource(R.string.quantity)) },
                onValueChange = { newValue ->
                    count = newValue.copy(
                        text = newValue.text.filter { it.isDigit() }.take(3)
                    )},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(
                    color = Gray900,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                enabled = enabled,
                modifier = Modifier.weight(1f).onFocusChanged { focusState ->
                    if (focusState.isFocused && !hasFocus) {
                        // Выделяем весь текст при первом фокусе
                        count = count.copy(
                            selection = TextRange(0, count.text.length)
                        )
                        hasFocus = true
                    } else if (!focusState.isFocused) {
                        hasFocus = false
                    }
                },
                shape = MaterialTheme.shapes.small,
            )

            // OK button
            Column(
                modifier = Modifier
                    .height(56.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                CommonHabitButton(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    text = "OK",
                    onClick = { onConfirm(count.text.toInt()) },
                    style = MaterialTheme.typography.bodyLarge,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.additionalColors.elementsAccent,
                    ),
                    enabled = enabled && count.text.toIntOrNull() != null
                )
            }
        }

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CommonHabitButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.note),
                icon = Icons.Default.Edit,
                onClick = onEditNote,
                enabled = enabled
            )
            CommonHabitButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.skip),
                icon = Icons.Default.SkipNext,
                onClick = onSkip,
                enabled = enabled
            )
        }
    }
}

@Composable
fun CommonHabitClearButton(
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
) {
    CommonHabitButton(
        modifier = modifier,
        text = stringResource(R.string.clear),
        icon = Icons.Default.Replay,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.additionalColors.elementsError,
        ),
        onClick = onCancel,
        enabled = enabled,
    )
}

@Composable
fun CommonHabitCompleteButton(
    onMarkComplete: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    CommonHabitButton(
        modifier = modifier,
        text = stringResource(R.string.complete),
        icon = Icons.Default.Check,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.additionalColors.elementsSuccess,
        ),
        onClick = onMarkComplete,
        enabled = enabled
    )
}

@Composable
fun CommonHabitNoteButton(
    onEditNote: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    CommonHabitButton(
        modifier = modifier,
        text = stringResource(R.string.note),
        icon = Icons.Default.Edit,
        onClick = onEditNote,
        enabled = enabled
    )
}

@Composable
fun CommonHabitSkipButton(
    onSkip: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    CommonHabitButton(
        modifier = modifier,
        text = stringResource(R.string.skip),
        icon = Icons.Default.SkipNext,
        onClick = onSkip,
        enabled = enabled
    )
}

// ========== Previews ==========

@Preview(name = "Default Content", showBackground = true)
@Composable
private fun DefaultExpandedContentPreview() {
    JuyemTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .width(300.dp)
        ) {
            DefaultExpandedContent()
        }
    }
}

@Preview(name = "Timer Content - Stopped", showBackground = true)
@Composable
private fun TimerExpandedContentStoppedPreview() {
    JuyemTheme {
        TimerExpandedContent(
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Counter Content", showBackground = true)
@Composable
private fun CounterExpandedContentPreview() {
    JuyemTheme {
        CounterExpandedContent(
            modifier = Modifier.padding(16.dp)
        )
    }
}
