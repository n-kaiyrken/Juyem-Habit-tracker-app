package kz.nkaiyrken.juyem.navigation

sealed class Screen(val route: String) {
    object HabitList : Screen("habit_list")
    object HabitDetail : Screen("habit_detail/{habitId}") {
        fun createRoute(habitId: Int) = "habit_detail/$habitId"
    }
    object CreateHabit : Screen("create_habit")
    object EditHabit : Screen("edit_habit/{habitId}") {
        fun createRoute(habitId: Int) = "edit_habit/$habitId"
    }
    object Settings : Screen("settings")
}