package kz.nkaiyrken.juyem.core.ui.widgets.button.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun DefaultHabitButtonContent(
    text: String?,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                )
            }
            if (icon != null && text != null) {
                Spacer(modifier = Modifier.width(8.dp))
            }
            text?.let {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    style = style,
                    color = color,
                )
            }
        }
    }
}
