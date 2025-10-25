package kz.nkaiyrken.juyem.core.ui.widgets.shimmer

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme

/**
 * Shimmer animation effect for loading placeholders.
 *
 * @param shimmerBody Composable content that receives animated brush for background
 */
@Composable
fun ShimmerAnimation(shimmerBody: @Composable (brush: Brush) -> Unit) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0F,
        targetValue = 1000F,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 900,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "shimmerTranslate",
    )

    val brush = Brush.linearGradient(
        colors = getShimmerColors(),
        start = Offset(10F, 10F),
        end = Offset(translateAnim, translateAnim),
    )
    shimmerBody(brush)
}

@Preview
@Composable
private fun ShimmerAnimationPreview() {
    JuyemTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ShimmerAnimation { brush ->
                Box(
                    modifier = Modifier
                        .size(width = 200.dp, height = 60.dp)
                        .background(
                            shape = MaterialTheme.shapes.medium,
                            brush = brush,
                        ),
                )
            }
        }
    }
}
