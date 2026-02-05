package kz.nkaiyrken.juyem.features.habits.presentation.allhabits.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors

@Composable
fun ReorderHabitItem(
    title: String,
    actionText: String,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit,
    icon: ImageVector = Icons.Default.DeleteOutline,
    onIconButtonClick: () -> Unit = {},
    dragHandleModifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.additionalColors.backgroundLight)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onIconButtonClick
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = dragHandleModifier
                    .padding(start = 16.dp)
                    .size(24.dp),
                tint = MaterialTheme.additionalColors.elementsLowContrast,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.additionalColors.elementsHighContrast,
            modifier = Modifier.weight(1f)
        )

        TextButton(
            onClick = onActionClick,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.additionalColors.elementsAccent,
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun ReorderHabitItemPreview() {
    JuyemTheme {
        ReorderHabitItem(
            title = "Тренировка",
            actionText = "Активировать",
            onActionClick = {},
            onIconButtonClick = {}
        )
    }
}
