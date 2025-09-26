package kz.nkaiyrken.juyem.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kz.nkaiyrken.juyem.features.habits.presentation.HabitScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { HabitScreen(modifier = Modifier.padding(16.dp)) }
        // TODO: Add more destinations
    }
}