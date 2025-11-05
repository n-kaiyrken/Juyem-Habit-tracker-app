package kz.nkaiyrken.juyem.core.ui.widgets.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors

/**
 * Top App Bar component with flexible configuration.
 *
 * @param modifier Modifier to be applied
 * @param title App bar title text
 * @param titleAlignment Title alignment: "start" (left) or "center"
 * @param navigationIcon Navigation icon (null to hide)
 * @param onNavigationClick Navigation icon click handler
 * @param actionText Optional action text button (e.g., "Детали")
 * @param onActionTextClick Action text click handler
 * @param actionIcon Optional action icon (e.g., More menu)
 * @param onActionIconClick Action icon click handler
 * @param actions Additional custom actions
 * @param backgroundColor Background color
 * @param contentColor Content color
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  JuyemTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    titleAlignment: TopAppBarAlignment = TopAppBarAlignment.Start,
    navigationIcon: ImageVector? = null,
    onNavigationClick: (() -> Unit)? = null,
    actionText: String? = null,
    onActionTextClick: (() -> Unit)? = null,
    actionIcon: ImageVector? = null,
    onActionIconClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    val appBarContent = @Composable {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = contentColor,
                )
            },
            modifier = modifier,
            navigationIcon = {
                if (navigationIcon != null && onNavigationClick != null) {
                    IconButton(onClick = onNavigationClick) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = "Navigate back",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.additionalColors.elementsAccent,
                        )
                    }
                }
            },
            actions = {
                // Action text button
                if (actionText != null && onActionTextClick != null) {
                    TextButton(onClick = onActionTextClick) {
                        Text(
                            text = actionText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.additionalColors.elementsAccent,
                        )
                    }
                }

                // Action icon button
                if (actionIcon != null && onActionIconClick != null) {
                    IconButton(onClick = onActionIconClick) {
                        Icon(
                            imageVector = actionIcon,
                            contentDescription = "Add action",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.additionalColors.elementsAccent,
                        )
                    }
                }

                // Custom actions
                actions()
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor,
                titleContentColor = contentColor,
                navigationIconContentColor = contentColor,
                actionIconContentColor = contentColor,
            ),
        )
    }

    when (titleAlignment) {
        TopAppBarAlignment.Start -> appBarContent()
        TopAppBarAlignment.Center -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = contentColor,
                    )
                },
                modifier = modifier,
                navigationIcon = {
                    if (navigationIcon != null && onNavigationClick != null) {
                        IconButton(onClick = onNavigationClick) {
                            Icon(
                                imageVector = navigationIcon,
                                contentDescription = "Navigate back",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.additionalColors.elementsAccent,
                            )
                        }
                    }
                },
                actions = {
                    // Action text button
                    if (actionText != null && onActionTextClick != null) {
                        TextButton(onClick = onActionTextClick) {
                            Text(
                                text = actionText,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.additionalColors.elementsAccent,
                            )
                        }
                    }

                    // Action icon button
                    if (actionIcon != null && onActionIconClick != null) {
                        IconButton(onClick = onActionIconClick) {
                            Icon(
                                imageVector = actionIcon,
                                contentDescription = "More actions",
                                modifier = Modifier.size(24.dp),
                                tint = contentColor,
                            )
                        }
                    }

                    // Custom actions
                    actions()
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = contentColor,
                    navigationIconContentColor = contentColor,
                    actionIconContentColor = contentColor,
                ),
            )
        }
    }
}

/**
 * Title alignment options for TopAppBar
 */
enum class TopAppBarAlignment {
    Start,
    Center
}

// ========== Previews ==========

@Preview(name = "With all actions", showBackground = true)
@Composable
private fun TopAppBarFullPreview() {
    JuyemTheme {
        JuyemTopAppBar(
            title = "Сегодня",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = {},
            actionText = "Детали",
            onActionTextClick = {},
            actionIcon = Icons.Default.MoreVert,
            onActionIconClick = {},
        )
    }
}

@Preview(name = "Without navigation", showBackground = true)
@Composable
private fun TopAppBarNoNavigationPreview() {
    JuyemTheme {
        JuyemTopAppBar(
            title = "Сегодня",
            actionText = "Детали",
            onActionTextClick = {},
            actionIcon = Icons.Default.MoreVert,
            onActionIconClick = {},
        )
    }
}

@Preview(name = "Center aligned", showBackground = true)
@Composable
private fun TopAppBarCenterPreview() {
    JuyemTheme {
        JuyemTopAppBar(
            title = "Мои привычки",
            titleAlignment = TopAppBarAlignment.Center,
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = {},
            actionIcon = Icons.Default.MoreVert,
            onActionIconClick = {},
        )
    }
}

@Preview(name = "Minimal", showBackground = true)
@Composable
private fun TopAppBarMinimalPreview() {
    JuyemTheme {
        JuyemTopAppBar(
            title = "Главная",
        )
    }
}
