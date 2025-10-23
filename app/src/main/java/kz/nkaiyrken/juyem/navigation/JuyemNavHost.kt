package kz.nkaiyrken.juyem.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kz.nkaiyrken.juyem.features.habits.presentation.HabitListScreen

@Composable
fun JuyemNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.HabitList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Habit List Screen
        composable(route = Screen.HabitList.route) {
            HabitListScreen()
        }
    }
}