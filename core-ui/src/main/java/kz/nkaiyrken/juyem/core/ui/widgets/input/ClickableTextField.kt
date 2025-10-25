package kz.nkaiyrken.juyem.core.ui.widgets.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors

/**
 * Clickable text field for selecting values (e.g., date picker, dropdown).
 * Not editable by keyboard, only by clicking.
 *
 * @param modifier Modifier to be applied
 * @param label Field label
 * @param selectedValue Currently selected value (optional)
 * @param enabled Whether field is enabled
 * @param icon Trailing icon resource ID (default: chevron down)
 * @param errorText Error message to display
 * @param hintText Hint text below field
 * @param shape Field shape
 * @param onClick Called when field is clicked
 */
@Composable
fun ClickableTextField(
    modifier: Modifier = Modifier,
    label: String,
    selectedValue: String? = null,
    enabled: Boolean = true,
    icon: Int? = null,
    errorText: String? = null,
    hintText: String? = null,
    shape: Shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier.alpha(if (enabled) 1.0f else 0.4f),
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = when {
                        errorText != null -> MaterialTheme.additionalColors.backgroundErrorLight1
                        else -> MaterialTheme.additionalColors.backgroundLight
                    },
                    shape = MaterialTheme.shapes.medium,
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    if (enabled) {
                        onClick.invoke()
                    }
                }
                .border(
                    width = 1.dp,
                    color = when {
                        errorText != null -> MaterialTheme.additionalColors.elementsError
                        !enabled -> MaterialTheme.additionalColors.elementsLowContrast
                        else -> Color.Transparent
                    },
                    shape = shape,
                )
                .padding(horizontal = 16.dp),
        ) {
            if (selectedValue.isNullOrEmpty()) {
                LabelWithoutSelectedValue(
                    label = label,
                    error = errorText != null,
                )
            } else {
                LabelWithSelectedValue(
                    label = label,
                    selectedValue = selectedValue,
                    error = errorText != null,
                )
            }
            icon?.let {
                Icon(
                    modifier = Modifier
                        .padding(vertical = 18.dp)
                        .size(24.dp),
                    imageVector = ImageVector.vectorResource(id = it),
                    contentDescription = null,
                    tint = MaterialTheme.additionalColors.elementsLowContrast,
                )
            }
        }
        hintText?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp),
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.additionalColors.elementsLowContrast,
            )
        }
        errorText?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp),
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.additionalColors.elementsError,
            )
        }
    }
}

@Composable
private fun RowScope.LabelWithoutSelectedValue(
    label: String,
    error: Boolean = false,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1.0f)
            .padding(vertical = 19.dp),
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        color = when {
            error -> MaterialTheme.additionalColors.elementsError
            else -> MaterialTheme.additionalColors.elementsLowContrast
        },
    )
}

@Composable
private fun RowScope.LabelWithSelectedValue(
    label: String,
    selectedValue: String,
    error: Boolean = false,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1.0f),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 9.dp),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = when {
                error -> MaterialTheme.additionalColors.elementsError
                else -> MaterialTheme.additionalColors.elementsLowContrast
            },
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 9.dp),
            text = selectedValue,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.additionalColors.elementsHighContrast,
        )
    }
}

@Preview
@Composable
private fun ClickableTextFieldPreview() {
    JuyemTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.additionalColors.backgroundPrimary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ClickableTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Select Date",
                hintText = "Choose a date from calendar",
                onClick = { },
            )

            ClickableTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Select Date",
                hintText = "Choose a date from calendar",
                selectedValue = "January 15, 2024",
                onClick = { },
            )

            ClickableTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Select Date",
                hintText = "Choose a date from calendar",
                selectedValue = "January 15, 2024",
                enabled = false,
                onClick = { },
            )

            ClickableTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Select Date",
                selectedValue = "Invalid Date",
                errorText = "Please select a valid date",
                onClick = { },
            )
        }
    }
}
