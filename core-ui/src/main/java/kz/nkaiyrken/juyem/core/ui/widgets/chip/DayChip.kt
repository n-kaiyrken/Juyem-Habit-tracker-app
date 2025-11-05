package kz.nkaiyrken.juyem.core.ui.widgets.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.nkaiyrken.juyem.core.ui.theme.Gray100
import kz.nkaiyrken.juyem.core.ui.theme.Gray500
import kz.nkaiyrken.juyem.core.ui.theme.Gray900
import kz.nkaiyrken.juyem.core.ui.theme.Green300
import kz.nkaiyrken.juyem.core.ui.theme.Green700
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.LightGray100
import kz.nkaiyrken.juyem.core.ui.theme.LightGray200
import kz.nkaiyrken.juyem.core.ui.theme.Red300
import kz.nkaiyrken.juyem.core.ui.theme.Red700

@Composable
fun DayChip(
    dayLabel: String,
    modifier: Modifier = Modifier,
    type: DayChipType = DayChipType.DEFAULT,
    state: DayChipState = DayChipState.DEFAULT,
    onClick: () -> Unit = {},
) {
    val colors = getDayChipColors(type)
    val shape = RoundedCornerShape(8.dp)

    val alpha = when (state) {
        DayChipState.DISABLED -> 0.6f
        else -> 1f
    }

    val borderStroke = if (state == DayChipState.FOCUS) BorderStroke(1.dp, Gray900) else null

    Box(
        modifier = modifier
            .size(40.dp)
            .clip(shape)
            .then(
                if (borderStroke != null) {
                    Modifier.border(borderStroke, shape)
                } else {
                    Modifier
                }
            )
            .background(colors.background.copy(alpha = alpha))
            .clickable(enabled = state != DayChipState.DISABLED, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayLabel,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.sp
            ),
            color = colors.content.copy(alpha = alpha)
        )
    }
}

enum class DayChipType {
    EMPTY,
    COMPLETED,
    SKIPPED,
    FAILED,
    DEFAULT,
}

enum class DayChipState {
    DEFAULT,
    DISABLED,
    FOCUS,
    HOVER
}

private data class DayChipColors(
    val background: Color,
    val content: Color
)

private fun getDayChipColors(type: DayChipType): DayChipColors {
    return when (type) {
        DayChipType.EMPTY -> DayChipColors(
            background = Gray100,
            content = Gray500
        )
        DayChipType.COMPLETED -> DayChipColors(
            background = Green300,
            content = Green700
        )
        DayChipType.SKIPPED -> DayChipColors(
            background = LightGray100,
            content = Gray500
        )
        DayChipType.FAILED -> DayChipColors(
            background = Red300,
            content = Red700
        )
        DayChipType.DEFAULT -> DayChipColors(
            background = LightGray200,
            content = Gray500
        )
    }
}

// ========== Previews ==========

@Preview(name = "Empty - Default", showBackground = true)
@Composable
private fun DayChipEmptyPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПН",
            type = DayChipType.EMPTY,
            state = DayChipState.DEFAULT
        )
    }
}

@Preview(name = "Empty - Focus", showBackground = true)
@Composable
private fun DayChipEmptyFocusPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПН",
            type = DayChipType.EMPTY,
            state = DayChipState.FOCUS
        )
    }
}

@Preview(name = "Empty - Disabled", showBackground = true)
@Composable
private fun DayChipEmptyDisabledPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПН",
            type = DayChipType.EMPTY,
            state = DayChipState.DISABLED
        )
    }
}

@Preview(name = "Default", showBackground = true)
@Composable
private fun DayChipDefaultPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ВТ",
            type = DayChipType.DEFAULT
        )
    }
}

@Preview(name = "Skip", showBackground = true)
@Composable
private fun DayChipSkipPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "СР",
            type = DayChipType.SKIPPED
        )
    }
}

@Preview(name = "Skip - Focus", showBackground = true)
@Composable
private fun DayChipSkipFocusPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "СР",
            type = DayChipType.SKIPPED,
            state = DayChipState.FOCUS
        )
    }
}

@Preview(name = "Skip - Disabled", showBackground = true)
@Composable
private fun DayChipSkipDisabledPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "СР",
            type = DayChipType.SKIPPED,
            state = DayChipState.DISABLED
        )
    }
}

@Preview(name = "Success", showBackground = true)
@Composable
private fun DayChipSuccessPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ЧТ",
            type = DayChipType.COMPLETED
        )
    }
}

@Preview(name = "Success - Focus", showBackground = true)
@Composable
private fun DayChipSuccessFocusPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ЧТ",
            type = DayChipType.COMPLETED,
            state = DayChipState.FOCUS
        )
    }
}

@Preview(name = "Success - Disabled", showBackground = true)
@Composable
private fun DayChipSuccessDisabledPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ЧТ",
            type = DayChipType.COMPLETED,
            state = DayChipState.DISABLED
        )
    }
}

@Preview(name = "Failure", showBackground = true)
@Composable
private fun DayChipFailurePreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПТ",
            type = DayChipType.FAILED
        )
    }
}

@Preview(name = "Failure - Focus", showBackground = true)
@Composable
private fun DayChipFailureFocusPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПТ",
            type = DayChipType.FAILED,
            state = DayChipState.FOCUS
        )
    }
}

@Preview(name = "Failure - Disabled", showBackground = true)
@Composable
private fun DayChipFailureDisabledPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПТ",
            type = DayChipType.FAILED,
            state = DayChipState.DISABLED
        )
    }
}

@Preview(name = "Week row", showBackground = true)
@Composable
private fun DayChipWeekRowPreview() {
    JuyemTheme {
        androidx.compose.foundation.layout.Row(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp)
        ) {
            DayChip("ПН", type = DayChipType.COMPLETED)
            DayChip("ВТ", type = DayChipType.COMPLETED)
            DayChip("СР", type = DayChipType.SKIPPED)
            DayChip("ЧТ", type = DayChipType.FAILED)
            DayChip("ПТ", type = DayChipType.SKIPPED, state = DayChipState.DISABLED)
            DayChip("СБ", type = DayChipType.EMPTY, state = DayChipState.DISABLED)
            DayChip("ВС", type = DayChipType.EMPTY)
        }
    }
}
