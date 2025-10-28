package kz.nkaiyrken.juyem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.widgets.toolbar.JuyemTopAppBar
import kz.nkaiyrken.juyem.core.ui.widgets.toolbar.rememberTopAppBarState
import kz.nkaiyrken.juyem.navigation.JuyemNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JuyemTheme {
                val navController = rememberNavController()
                val topAppBarState = rememberTopAppBarState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (topAppBarState.isVisible) {
                            JuyemTopAppBar(
                                title = topAppBarState.title,
                                titleAlignment = topAppBarState.titleAlignment,
                                navigationIcon = if (topAppBarState.showNavigationIcon) {
                                    topAppBarState.navigationIcon
                                } else null,
                                onNavigationClick = topAppBarState.onNavigationClick,
                                actionText = topAppBarState.actionText,
                                onActionTextClick = topAppBarState.onActionTextClick,
                                actionIcon = topAppBarState.actionIcon,
                                onActionIconClick = topAppBarState.onActionIconClick,
                            )
                        }
                    }
                ) { paddingValues ->
                    JuyemNavHost(
                        navController = navController,
                        topAppBarState = topAppBarState,
                        contentPadding = paddingValues,
                    )
                }
            }
        }
    }
}