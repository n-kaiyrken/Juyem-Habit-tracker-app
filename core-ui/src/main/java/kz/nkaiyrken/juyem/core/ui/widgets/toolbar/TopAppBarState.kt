package kz.nkaiyrken.juyem.core.ui.widgets.toolbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * State holder for TopAppBar configuration.
 * Allows screens to dynamically configure the app-level TopAppBar.
 */
class TopAppBarState {
    var title by mutableStateOf("")
        private set

    var titleAlignment by mutableStateOf(TopAppBarAlignment.Start)
        private set

    var showNavigationIcon by mutableStateOf(false)
        private set

    var navigationIcon by mutableStateOf(Icons.Default.Menu)
        private set

    var onNavigationClick by mutableStateOf<(() -> Unit)?>(null)
        private set

    var actionText by mutableStateOf<String?>(null)
        private set

    var onActionTextClick by mutableStateOf<(() -> Unit)?>(null)
        private set

    var actionIcon by mutableStateOf<ImageVector?>(null)
        private set

    var onActionIconClick by mutableStateOf<(() -> Unit)?>(null)
        private set

    var isVisible by mutableStateOf(true)
        private set

    /**
     * Update TopAppBar configuration.
     */
    fun update(
        title: String = this.title,
        titleAlignment: TopAppBarAlignment = this.titleAlignment,
        showNavigationIcon: Boolean = this.showNavigationIcon,
        navigationIcon: ImageVector = this.navigationIcon,
        onNavigationClick: (() -> Unit)? = this.onNavigationClick,
        actionText: String? = this.actionText,
        onActionTextClick: (() -> Unit)? = this.onActionTextClick,
        actionIcon: ImageVector? = this.actionIcon,
        onActionIconClick: (() -> Unit)? = this.onActionIconClick,
        isVisible: Boolean = this.isVisible,
    ) {
        this.title = title
        this.titleAlignment = titleAlignment
        this.showNavigationIcon = showNavigationIcon
        this.navigationIcon = navigationIcon
        this.onNavigationClick = onNavigationClick
        this.actionText = actionText
        this.onActionTextClick = onActionTextClick
        this.actionIcon = actionIcon
        this.onActionIconClick = onActionIconClick
        this.isVisible = isVisible
    }

    /**
     * Reset TopAppBar to default state.
     */
    fun reset() {
        title = ""
        titleAlignment = TopAppBarAlignment.Start
        showNavigationIcon = false
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
        onNavigationClick = null
        actionText = null
        onActionTextClick = null
        actionIcon = null
        onActionIconClick = null
        isVisible = true
    }
}

/**
 * Remember TopAppBar state.
 */
@Composable
fun rememberTopAppBarState(): TopAppBarState {
    return remember { TopAppBarState() }
}
