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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.button.common.DefaultProgressButtonContent
import kz.nkaiyrken.juyem.core.ui.widgets.button.common.zeroElevation

/**
 * Plain text button with transparent background.
 * Text color changes on press and disabled states.
 *
 * @param text Button text label
 * @param modifier Modifier to be applied to the button
 * @param enabled Controls the enabled state of the button
 * @param loading Shows loading indicator when true
 * @param onClick Called when the button is clicked
 * @param style Text style for the button label
 * @param colors Custom colors for the button
 * @param shape Button shape
 * @param content Custom content for the button (overrides default text + loading)
 * @param contentPadding Internal padding for button content
 */
@Composable
fun CommonPlainButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit,
    style: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
    colors: ButtonColors? = null,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    content: @Composable RowScope.() -> Unit = {
        val contentInteractionSource = remember { MutableInteractionSource() }
        val isContentPressed by contentInteractionSource.collectIsPressedAsState()
        DefaultProgressButtonContent(
            text = text,
            style = style,
            color = when {
                !enabled -> MaterialTheme.additionalColors.backgroundAccent.copy(alpha = 0.8f)
                isContentPressed -> MaterialTheme.additionalColors.shadesAccentDark2
                else -> MaterialTheme.additionalColors.backgroundAccent
            },
            loading = loading,
            modifier = Modifier.height(24.dp),
        )
    },
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(),
        colors = colors ?: ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
        content = content,
        shape = shape,
        contentPadding = contentPadding,
    )
}

@Preview
@Composable
private fun CommonPlainButtonPreview() {
    JuyemTheme {
        var loading by remember { mutableStateOf(false) }
        CommonPlainButton(
            text = "Plain Button",
            modifier = Modifier.width(140.dp),
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
private fun CommonPlainButtonDisabledPreview() {
    JuyemTheme {
        CommonPlainButton(
            text = "Disabled",
            modifier = Modifier.width(140.dp),
            enabled = false,
            onClick = {},
        )
    }
}
