package kz.nkaiyrken.juyem.core.ui.widgets.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.shimmer.ShimmerAnimation

/**
 * Toolbar for bottom sheets with title and close button.
 *
 * @param modifier Modifier to be applied
 * @param title Bottom sheet title (optional)
 * @param isBackButtonVisible Show/hide close button
 * @param onBackClick Callback when close button is clicked
 * @param loading Show loading shimmer effect instead of title
 */
@Composable
fun BottomSheetToolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    isBackButtonVisible: Boolean,
    onBackClick: () -> Unit,
    loading: Boolean = false,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (loading) {
            ShimmerAnimation { brush ->
                Box(
                    modifier = Modifier
                        .size(width = 148.dp, height = 32.dp)
                        .background(
                            shape = MaterialTheme.shapes.medium,
                            brush = brush,
                        ),
                )
            }
        } else {
            title?.let {
                Text(
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.additionalColors.coreBlack,
                    text = it,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1F))

        if (isBackButtonVisible) {
            ExitButton(onClick = onBackClick)
        }
    }
}

@Composable
private fun ExitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector = Icons.Default.Close,
) {
    IconButton(
        modifier = modifier.size(24.dp),
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Close",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.additionalColors.elementsLowContrast,
        )
    }
}

@Preview
@Composable
private fun BottomSheetToolbarPreview() {
    JuyemTheme {
        BottomSheetToolbar(
            modifier = Modifier.fillMaxWidth(),
            isBackButtonVisible = true,
            title = "Choose Language",
            loading = false,
            onBackClick = { },
        )
    }
}

@Preview
@Composable
private fun BottomSheetToolbarLoadingPreview() {
    JuyemTheme {
        BottomSheetToolbar(
            modifier = Modifier.fillMaxWidth(),
            isBackButtonVisible = true,
            title = "Choose Language",
            loading = true,
            onBackClick = { },
        )
    }
}
