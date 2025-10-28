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
import kz.nkaiyrken.juyem.core.ui.theme.Blue500
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
    type: DayChipType = DayChipType.Default,
    state: DayChipState = DayChipState.Default,
    onClick: () -> Unit = {},
) {
    val colors = getDayChipColors(type)
    val shape = RoundedCornerShape(8.dp)

    val alpha = when (state) {
        DayChipState.Disabled -> 0.6f
        else -> 1f
    }

    val borderStroke = if (state == DayChipState.Focus) BorderStroke(1.dp, Gray900) else null

    Box(
        modifier = modifier
            .size(32.dp)
            .clip(shape)
            .then(
                if (borderStroke != null) {
                    Modifier.border(borderStroke, shape)
                } else {
                    Modifier
                }
            )
            .background(colors.background.copy(alpha = alpha))
            .clickable(enabled = state != DayChipState.Disabled, onClick = onClick),
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
    Empty,
    Success,
    Skip,
    Failure,
    Default
}

enum class DayChipState {
    Default,
    Disabled,
    Focus,
    Hover
}

private data class DayChipColors(
    val background: Color,
    val content: Color
)

private fun getDayChipColors(type: DayChipType): DayChipColors {
    return when (type) {
        DayChipType.Empty -> DayChipColors(
            background = Gray100,
            content = Gray500
        )
        DayChipType.Success -> DayChipColors(
            background = Green300,
            content = Green700
        )
        DayChipType.Skip -> DayChipColors(
            background = LightGray100,
            content = Gray500
        )
        DayChipType.Failure -> DayChipColors(
            background = Red300,
            content = Red700
        )
        DayChipType.Default -> DayChipColors(
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
            type = DayChipType.Empty,
            state = DayChipState.Default
        )
    }
}

@Preview(name = "Empty - Focus", showBackground = true)
@Composable
private fun DayChipEmptyFocusPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПН",
            type = DayChipType.Empty,
            state = DayChipState.Focus
        )
    }
}

@Preview(name = "Empty - Disabled", showBackground = true)
@Composable
private fun DayChipEmptyDisabledPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПН",
            type = DayChipType.Empty,
            state = DayChipState.Disabled
        )
    }
}

@Preview(name = "Default", showBackground = true)
@Composable
private fun DayChipDefaultPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ВТ",
            type = DayChipType.Default
        )
    }
}

@Preview(name = "Skip", showBackground = true)
@Composable
private fun DayChipSkipPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "СР",
            type = DayChipType.Skip
        )
    }
}

@Preview(name = "Skip - Focus", showBackground = true)
@Composable
private fun DayChipSkipFocusPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "СР",
            type = DayChipType.Skip,
            state = DayChipState.Focus
        )
    }
}

@Preview(name = "Skip - Disabled", showBackground = true)
@Composable
private fun DayChipSkipDisabledPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "СР",
            type = DayChipType.Skip,
            state = DayChipState.Disabled
        )
    }
}

@Preview(name = "Success", showBackground = true)
@Composable
private fun DayChipSuccessPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ЧТ",
            type = DayChipType.Success
        )
    }
}

@Preview(name = "Success - Focus", showBackground = true)
@Composable
private fun DayChipSuccessFocusPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ЧТ",
            type = DayChipType.Success,
            state = DayChipState.Focus
        )
    }
}

@Preview(name = "Success - Disabled", showBackground = true)
@Composable
private fun DayChipSuccessDisabledPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ЧТ",
            type = DayChipType.Success,
            state = DayChipState.Disabled
        )
    }
}

@Preview(name = "Failure", showBackground = true)
@Composable
private fun DayChipFailurePreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПТ",
            type = DayChipType.Failure
        )
    }
}

@Preview(name = "Failure - Focus", showBackground = true)
@Composable
private fun DayChipFailureFocusPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПТ",
            type = DayChipType.Failure,
            state = DayChipState.Focus
        )
    }
}

@Preview(name = "Failure - Disabled", showBackground = true)
@Composable
private fun DayChipFailureDisabledPreview() {
    JuyemTheme {
        DayChip(
            dayLabel = "ПТ",
            type = DayChipType.Failure,
            state = DayChipState.Disabled
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
            DayChip("ПН", type = DayChipType.Success)
            DayChip("ВТ", type = DayChipType.Success)
            DayChip("СР", type = DayChipType.Skip)
            DayChip("ЧТ", type = DayChipType.Failure)
            DayChip("ПТ", type = DayChipType.Empty)
            DayChip("СБ", type = DayChipType.Empty)
            DayChip("ВС", type = DayChipType.Empty)
        }
    }
}
