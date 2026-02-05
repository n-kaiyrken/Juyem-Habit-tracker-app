package kz.nkaiyrken.juyem.core.ui.widgets.input

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors

private const val EMPTY_VALUE = ""

/**
 * Common input text field with label, icons, error states.
 *
 * @param modifier Modifier to be applied
 * @param text Current text value
 * @param isClearIconVisible Show clear icon when text is not empty
 * @param onValueChange Callback when text changes
 * @param textStyle Text style for input
 * @param enabled Whether field is enabled
 * @param labelTextStyle Label text style
 * @param labelText Label text
 * @param placeholderText Placeholder text (optional)
 * @param leadingIconRes Leading icon drawable resource
 * @param onClickLeadingIcon Callback when leading icon is clicked
 * @param trailingIconRes Trailing icon drawable resource
 * @param onClickTrailingIcon Callback when trailing icon is clicked
 * @param errorText Error message to display
 * @param shape Field shape
 * @param maxLines Maximum number of lines
 * @param colors Custom text field colors
 * @param keyboardType Keyboard type
 * @param visualTransformation Visual transformation for text
 * @param imeAction IME action
 * @param keyboardActions Keyboard actions
 * @param validSymbols List of valid symbols (filters input)
 */
@Composable
fun CommonInputField(
    modifier: Modifier = Modifier,
    text: String,
    isClearIconVisible: Boolean = false,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    enabled: Boolean = true,
    labelTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    labelText: String,
    placeholderText: String? = null,
    @DrawableRes leadingIconRes: Int? = null,
    onClickLeadingIcon: (() -> Unit)? = null,
    @DrawableRes trailingIconRes: Int? = null,
    onClickTrailingIcon: (() -> Unit)? = null,
    errorText: String? = null,
    shape: Shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
    maxLines: Int = 1,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.additionalColors.elementsHighContrast,
        unfocusedTextColor = MaterialTheme.additionalColors.elementsHighContrast,
        disabledTextColor = MaterialTheme.additionalColors.elementsLowContrast,
        focusedContainerColor = MaterialTheme.additionalColors.backgroundLight,
        unfocusedContainerColor = MaterialTheme.additionalColors.backgroundLight,
        disabledContainerColor = MaterialTheme.additionalColors.backgroundLight,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        errorContainerColor = MaterialTheme.additionalColors.backgroundErrorLight1,
        cursorColor = MaterialTheme.additionalColors.elementsAccent,
    ),
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    validSymbols: List<String>? = null,
) {
    Column(
        modifier = modifier.alpha(if (enabled) 1.0f else 0.4f),
    ) {
        var isFocused by remember { mutableStateOf(false) }

        val leadingIcon = iconGenerating(
            iconRes = leadingIconRes,
            color = when {
                errorText != null -> MaterialTheme.additionalColors.elementsError
                else -> MaterialTheme.additionalColors.elementsLowContrast
            },
            onClick = onClickLeadingIcon,
        )
        val trailingIcon = iconGenerating(
            iconRes = if (isClearIconVisible) android.R.drawable.ic_menu_close_clear_cancel else trailingIconRes,
            color = when {
                errorText != null -> MaterialTheme.additionalColors.elementsError
                else -> MaterialTheme.additionalColors.elementsLowContrast
            },
            onClick = {
                if (isClearIconVisible) {
                    onValueChange(EMPTY_VALUE)
                } else {
                    onClickTrailingIcon?.invoke()
                }
            },
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = when {
                        errorText != null -> MaterialTheme.additionalColors.elementsError
                        isFocused -> MaterialTheme.additionalColors.elementsLowContrast
                        !enabled -> MaterialTheme.additionalColors.elementsLowContrast
                        else -> Color.Transparent
                    },
                    shape = shape,
                )
                .onFocusChanged {
                    isFocused = it.hasFocus
                },
            value = text,
            onValueChange = { newText ->
                if (validSymbols != null) {
                    onValueChange.invoke(newText.filter { validSymbols.contains(it.toString()) })
                } else {
                    onValueChange.invoke(newText)
                }
            },
            textStyle = textStyle,
            enabled = enabled,
            label = {
                CommonInputDefaultLabel(
                    text = labelText,
                    style = if (isFocused || text.isNotEmpty()) {
                        MaterialTheme.typography.bodyMedium
                    } else {
                        labelTextStyle
                    },
                    color = if (errorText != null) {
                        MaterialTheme.additionalColors.elementsError
                    } else {
                        MaterialTheme.additionalColors.elementsLowContrast
                    },
                )
            },
            placeholder = placeholderText?.let {
                {
                    CommonInputDefaultPlaceholder(
                        text = placeholderText,
                        color = when {
                            errorText != null -> MaterialTheme.additionalColors.elementsError
                            !enabled -> MaterialTheme.additionalColors.elementsLowContrast
                            else -> MaterialTheme.additionalColors.elementsHighContrast
                        },
                    )
                }
            },
            leadingIcon = leadingIcon,
            trailingIcon = if (isFocused && text.isNotEmpty()) trailingIcon else null,
            isError = errorText != null,
            shape = shape,
            maxLines = maxLines,
            colors = colors,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            visualTransformation = if (isFocused || text.isNotEmpty()) visualTransformation else VisualTransformation.None,
            keyboardActions = keyboardActions,
        )
        if (!errorText.isNullOrBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp),
                text = errorText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.additionalColors.elementsError,
            )
        }
    }
}

@Composable
private fun iconGenerating(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int?,
    color: Color = MaterialTheme.additionalColors.coreBlack,
    onClick: (() -> Unit)? = null,
): @Composable (() -> Unit)? {
    if (iconRes == null) return null

    return {
        CommonInputIcon(
            modifier = modifier,
            iconRes = iconRes,
            color = color,
            onClick = onClick,
        )
    }
}

@Composable
private fun CommonInputIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    color: Color = MaterialTheme.additionalColors.coreBlack,
    onClick: (() -> Unit)? = null,
) {
    IconButton(onClick = { onClick?.invoke() }, enabled = onClick != null) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = modifier.size(24.dp),
            tint = color,
        )
    }
}

@Composable
private fun CommonInputDefaultLabel(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.additionalColors.coreBlack,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
    )
}

@Composable
fun CommonInputDefaultPlaceholder(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = MaterialTheme.additionalColors.coreBlack,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
    )
}

@Preview
@Composable
private fun CommonInputFieldPreview() {
    var text by remember { mutableStateOf("") }
    JuyemTheme {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            CommonInputField(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                labelText = "Field Name",
                onValueChange = { text = it },
                placeholderText = "Enter text",
            )

            CommonInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = text,
                labelText = "Field Name",
                onValueChange = { text = it },
                placeholderText = "Enter text",
                errorText = "This field is required",
            )

            CommonInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = "Disabled",
                labelText = "Field Name",
                onValueChange = { },
                enabled = false,
            )
        }
    }
}
