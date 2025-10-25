package kz.nkaiyrken.juyem.core.ui.widgets.progress

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors

/**
 * Custom rotating progress bar with animated bars.
 *
 * @param modifier Modifier to be applied
 * @param color Color of the progress bars
 * @param count Number of bars in the circle
 */
@Composable
fun CustomProgressBar(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.additionalColors.coreWhite,
    count: Int = 8,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "progressRotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(count * 80, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "angle",
    )

    Canvas(modifier = modifier.size(24.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val width = size.width * .3f
        val height = size.height / 12

        val cornerRadius = width.coerceAtMost(height) / 2

        // Draw background bars
        for (i in 0..360 step 360 / count) {
            rotate(i.toFloat()) {
                drawRoundRect(
                    color = color.copy(alpha = .3f),
                    topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                    size = Size(width, height),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                )
            }
        }

        // Draw animated bars
        val coefficient = 360f / count

        for (i in 1..4) {
            rotate((angle.toInt() + i) * coefficient) {
                drawRoundRect(
                    color = color,
                    topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                    size = Size(width, height),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                )
            }
        }
    }
}

@Preview
@Composable
private fun CustomProgressBarPreview() {
    JuyemTheme {
        CustomProgressBar()
    }
}

@Preview
@Composable
private fun CustomProgressBarAccentPreview() {
    JuyemTheme {
        CustomProgressBar(
            color = MaterialTheme.additionalColors.backgroundAccent,
        )
    }
}
