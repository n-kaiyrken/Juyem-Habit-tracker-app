package kz.nkaiyrken.juyem.core.ui.widgets.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors

/**
 * Standard top app bar with navigation and actions.
 *
 * @param modifier Modifier to be applied
 * @param title Toolbar title text
 * @param navigateUp Navigation back callback (null hides back button)
 * @param navigateIcon Navigation icon (default: back arrow)
 * @param actions Action buttons on the right side
 * @param backgroundColor Toolbar background color
 * @param contentColor Toolbar content color
 * @param elevation Toolbar elevation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigateUp: (() -> Unit)? = null,
    navigateIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    elevation: Dp = 0.dp,
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = {
            if (navigateUp != null) {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = navigateIcon,
                        contentDescription = "Navigate back",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.additionalColors.elementsAccent,
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = contentColor,
        ),
    )
}

@Preview
@Composable
private fun ToolbarPreview() {
    JuyemTheme {
        Toolbar(
            title = "My Habits",
            navigateUp = {},
        )
    }
}

@Preview
@Composable
private fun ToolbarWithoutBackPreview() {
    JuyemTheme {
        Toolbar(
            title = "Home",
        )
    }
}
