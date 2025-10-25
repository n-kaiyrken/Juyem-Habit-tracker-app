package kz.nkaiyrken.juyem.core.ui.widgets.shimmer

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors

/**
 * Returns shimmer gradient colors for loading placeholders.
 */
@Composable
fun getShimmerColors() = listOf(
    MaterialTheme.additionalColors.backgroundLight,
    MaterialTheme.additionalColors.elementsLight,
    MaterialTheme.additionalColors.backgroundLight,
)
