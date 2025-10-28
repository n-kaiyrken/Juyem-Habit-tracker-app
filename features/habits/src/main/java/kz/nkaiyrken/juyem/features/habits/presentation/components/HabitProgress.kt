package kz.nkaiyrken.juyem.features.habits.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.nkaiyrken.juyem.core.ui.theme.Gray900
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.LightGray200

@Composable
fun HabitCardProgress(
    currentProgressValue: Int,
    goalProgressValue: Int,
    isTimer: Boolean,
    modifier: Modifier = Modifier,
    unit: String? = null
) {
    Box(
        modifier = modifier
            .background(
                color = LightGray200,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = getProgressValue(currentProgressValue, isTimer),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Gray900
            )
            Text(
                text = "/",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = Gray900
            )
            Text(
                text = getProgressValue(goalProgressValue, isTimer),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = Gray900
            )
            if (unit != null) {
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = Gray900
                )
            }
        }
    }
}

private fun getProgressValue(value: Int, isTimer: Boolean): String {
    var currentValue = value.toString()
    if (isTimer) {
        val hours = value / 3600
        val minutes = (value % 3600) / 60
        val secs = value % 60
        currentValue = String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
    return currentValue
}

// ========== Previews ==========

@Preview(showBackground = true)
@Composable
private fun HabitCardProgressPreview() {
    JuyemTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            HabitCardProgress(
                currentProgressValue = 3,
                goalProgressValue = 8,
                isTimer = false,
                unit = "стаканов"
            )

            HabitCardProgress(
                currentProgressValue = 45,
                goalProgressValue = 60,
                isTimer = false,
                unit = "минут"
            )

            HabitCardProgress(
                currentProgressValue = 10,
                goalProgressValue = 100,
                isTimer = true
            )
        }
    }
}
