package kz.nkaiyrken.juyem.core.ui.widgets.switcher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors

/**
 * Custom switch/toggle component with app theme colors.
 *
 * @param checked Current checked state
 * @param modifier Modifier to be applied
 * @param colors Custom switch colors
 * @param enabled Whether switch is enabled
 * @param onCheckedChange Callback when checked state changes
 */
@Composable
fun Switcher(
    checked: Boolean,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.additionalColors.elementsAccent,
        checkedTrackColor = MaterialTheme.additionalColors.elementsAccentLight3,
        uncheckedThumbColor = MaterialTheme.additionalColors.coreWhite,
        uncheckedTrackColor = MaterialTheme.additionalColors.elementsLowContrast,
    ),
    enabled: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)?,
) {
    Switch(
        modifier = modifier,
        checked = checked,
        colors = colors,
        enabled = enabled,
        onCheckedChange = onCheckedChange,
    )
}

@Preview
@Composable
private fun SwitcherPreview() {
    JuyemTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.additionalColors.coreWhite)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Switcher(
                checked = true,
                onCheckedChange = null,
            )
            Switcher(
                checked = false,
                onCheckedChange = null,
            )
            Switcher(
                checked = true,
                enabled = false,
                onCheckedChange = null,
            )
        }
    }
}
