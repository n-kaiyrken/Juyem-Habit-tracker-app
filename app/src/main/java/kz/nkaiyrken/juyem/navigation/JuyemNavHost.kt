package kz.nkaiyrken.juyem.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kz.nkaiyrken.juyem.core.ui.widgets.toolbar.TopAppBarState
import kz.nkaiyrken.juyem.features.habits.presentation.HabitListScreen

@Composable
fun JuyemNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    topAppBarState: TopAppBarState,
    contentPadding: PaddingValues = PaddingValues(),
    startDestination: String = Screen.HabitList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.padding(contentPadding)
    ) {
        // Habit List Screen
        composable(route = Screen.HabitList.route) {
            HabitListScreen(
                topAppBarState = topAppBarState,
                onNavigateToDetail = { habitId ->
                    navController.navigate(Screen.HabitDetail.createRoute(habitId))
                },
                onNavigateToCreate = {
                    navController.navigate(Screen.CreateHabit.route)
                }
            )
        }
    }
}