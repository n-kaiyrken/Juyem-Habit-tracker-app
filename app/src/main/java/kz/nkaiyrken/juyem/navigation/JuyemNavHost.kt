package kz.nkaiyrken.juyem.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kz.nkaiyrken.juyem.core.ui.widgets.toolbar.TopAppBarState
import kz.nkaiyrken.juyem.features.habits.presentation.HabitListScreen
import kz.nkaiyrken.juyem.features.habits.presentation.allhabits.archive.ArchiveScreen
import kz.nkaiyrken.juyem.features.habits.presentation.allhabits.reorder.ReorderScreen
import kz.nkaiyrken.juyem.features.habits.presentation.createhabit.CreateHabitScreen

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
                    navController.navigate(Screen.HabitDetail.createRoute(habitId)){
                        launchSingleTop = true
                    }
                },
                onNavigateToCreate = {
                    navController.navigate(Screen.CreateHabit.route){
                        launchSingleTop = true
                    }
                },
                openDrawer = {
                    navController.navigate(Screen.Reorder.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // Reorder Screen
        composable(route = Screen.Reorder.route) {
            ReorderScreen(
                topAppBarState = topAppBarState,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCreate = { navController.navigate(Screen.CreateHabit.route){
                    launchSingleTop = true
                } },
                onNavigateToArchive = { navController.navigate(Screen.Archive.route){
                    launchSingleTop = true
                } },
            )
        }

        // Archive Screen
        composable(route = Screen.Archive.route) {
            ArchiveScreen(
                topAppBarState = topAppBarState,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        // Create Habit Screen
        composable(route = Screen.CreateHabit.route) {
            CreateHabitScreen(
                topAppBarState = topAppBarState,
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}