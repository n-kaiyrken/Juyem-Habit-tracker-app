package kz.nkaiyrken.juyem.core.ui.widgets.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.button.common.DefaultHabitButtonContent

@Composable
fun CommonHabitButton(
    text: String?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.additionalColors.elementsHighContrast,
    ),
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    icon: ImageVector? = null,
    content: @Composable RowScope.() -> Unit = {
        DefaultHabitButtonContent(
            text = text,
            style = style,
            color = if (enabled) {
                colors.contentColor
            } else {
                colors.contentColor.copy(alpha = 0.8f)
            },
            modifier = Modifier.height(24.dp),
            icon = icon,
        )
    },
    contentPadding: PaddingValues = PaddingValues(vertical = 6.dp, horizontal = 6.dp),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) {
                MaterialTheme.additionalColors.shadesGray200
            } else {
                MaterialTheme.additionalColors.backgroundDark
            },
            disabledContainerColor = MaterialTheme.additionalColors.backgroundDark.copy(alpha = 0.64f),
        ),
        interactionSource = interactionSource,
        content = content,
        shape = shape,
        contentPadding = contentPadding,
    )
}

@Preview
@Composable
private fun CommonHabitButtonSuccessPreview() {
    JuyemTheme {
        CommonHabitButton(
            text = "Complete",
            modifier = Modifier.width(140.dp),
            enabled = true,
            onClick = {},
            icon = Icons.Default.Check,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.additionalColors.elementsSuccess,
                disabledContentColor = MaterialTheme.additionalColors.elementsSuccess.copy(alpha = 0.64f),
            ),
        )
    }
}

@Preview
@Composable
private fun CommonHabitButtonCancelPreview() {
    JuyemTheme {
        CommonHabitButton(
            text = "Cancel",
            modifier = Modifier.width(140.dp),
            enabled = true,
            onClick = {},
            icon = Icons.Default.Replay,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.additionalColors.elementsError,
                disabledContentColor = MaterialTheme.additionalColors.elementsError.copy(alpha = 0.64f),
            ),
        )
    }
}

@Preview
@Composable
private fun CommonHabitButtonSkipPreview() {
    JuyemTheme {
        CommonHabitButton(
            text = "Skip",
            modifier = Modifier.width(140.dp),
            enabled = true,
            onClick = {},
            icon = Icons.Default.SkipNext,
        )
    }
}
