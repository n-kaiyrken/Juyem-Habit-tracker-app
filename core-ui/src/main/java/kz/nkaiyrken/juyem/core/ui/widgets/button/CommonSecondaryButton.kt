package kz.nkaiyrken.juyem.core.ui.widgets.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.button.common.DefaultProgressButtonContent
import kz.nkaiyrken.juyem.core.ui.widgets.button.common.zeroElevation

/**
 * Secondary button with light accent background.
 * Supports optional leading icon.
 *
 * @param text Button text label
 * @param modifier Modifier to be applied to the button
 * @param enabled Controls the enabled state of the button
 * @param loading Shows loading indicator when true
 * @param onClick Called when the button is clicked
 * @param style Text style for the button label
 * @param colors Custom colors for the button
 * @param shape Button shape
 * @param leadingIcon Optional icon to display before text
 * @param content Custom content for the button (overrides default text + icon + loading)
 * @param contentPadding Internal padding for button content
 */
@Composable
fun CommonSecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit,
    style: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
    colors: ButtonColors? = null,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    leadingIcon: ImageVector? = null,
    content: @Composable RowScope.() -> Unit = {
        val contentInteractionSource = remember { MutableInteractionSource() }
        val isContentPressed by contentInteractionSource.collectIsPressedAsState()
        DefaultProgressButtonContent(
            text = text,
            style = style,
            color = when {
                !enabled -> MaterialTheme.additionalColors.backgroundAccent.copy(alpha = 0.5f)
                isContentPressed -> MaterialTheme.additionalColors.shadesAccentDark2
                else -> MaterialTheme.additionalColors.backgroundAccent
            },
            loading = loading,
            icon = leadingIcon,
            modifier = Modifier.height(24.dp),
        )
    },
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(),
        colors = colors ?: ButtonDefaults.buttonColors(
            containerColor = if (isPressed) {
                MaterialTheme.additionalColors.backgroundAccentLight2
            } else {
                MaterialTheme.additionalColors.backgroundAccentLight1
            },
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.additionalColors.backgroundAccentLight1,
        ),
        interactionSource = interactionSource,
        content = content,
        shape = shape,
        contentPadding = contentPadding,
    )
}

@Preview
@Composable
private fun CommonSecondaryButtonPreview() {
    JuyemTheme {
        var loading by remember { mutableStateOf(false) }
        CommonSecondaryButton(
            text = "Secondary Button",
            modifier = Modifier.width(180.dp),
            enabled = true,
            onClick = {
                loading = !loading
            },
            loading = loading,
        )
    }
}

@Preview
@Composable
private fun CommonSecondaryButtonDisabledPreview() {
    JuyemTheme {
        CommonSecondaryButton(
            text = "Disabled",
            modifier = Modifier.width(160.dp),
            enabled = false,
            onClick = {},
        )
    }
}
