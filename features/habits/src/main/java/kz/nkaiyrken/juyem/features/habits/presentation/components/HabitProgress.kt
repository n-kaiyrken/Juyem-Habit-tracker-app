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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.nkaiyrken.juyem.core.ui.theme.Gray900
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.LightGray200
import kz.nkaiyrken.juyem.features.habits.R
import kz.nkaiyrken.juyem.features.habits.presentation.models.DailyProgressUiModel
import kz.nkaiyrken.juyem.features.habits.presentation.models.NumericProgress

@Composable
fun HabitCardProgress(
    progress: NumericProgress,
    modifier: Modifier = Modifier
) {

    val currentText = when (progress) {
        is DailyProgressUiModel.Timer -> formatTime(progress.currentValue)
        is DailyProgressUiModel.Counter -> progress.currentValue.toString()
    }

    val goalText = when (progress) {
        is DailyProgressUiModel.Timer -> formatTime(progress.goalValue)
        is DailyProgressUiModel.Counter -> progress.goalValue.toString()
    }

    val unit = when (progress) {
        is DailyProgressUiModel.Counter -> progress.unit
        is DailyProgressUiModel.Timer -> null
    }

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
                text = currentText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Gray900
            )
            Text(
                text = stringResource(R.string.forward_slash),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = Gray900
            )
            Text(
                text = goalText,
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

/**
 * Formats time in seconds to HH:MM:SS format
 */
private fun formatTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
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
                progress = DailyProgressUiModel.Counter(
                    habitId = 1,
                    date = java.time.LocalDate.now(),
                    status = kz.nkaiyrken.juyem.core.DailyProgressStatus.PARTIAL,
                    currentValue = 3,
                    goalValue = 8,
                    unit = "стаканов"
                )
            )

            HabitCardProgress(
                progress = DailyProgressUiModel.Counter(
                    habitId = 2,
                    date = java.time.LocalDate.now(),
                    status = kz.nkaiyrken.juyem.core.DailyProgressStatus.PARTIAL,
                    currentValue = 45,
                    goalValue = 60,
                    unit = "минут"
                )
            )

            HabitCardProgress(
                progress = DailyProgressUiModel.Timer(
                    habitId = 3,
                    date = java.time.LocalDate.now(),
                    status = kz.nkaiyrken.juyem.core.DailyProgressStatus.PARTIAL,
                    currentValue = 3610,
                    goalValue = 7200
                )
            )
        }
    }
}
