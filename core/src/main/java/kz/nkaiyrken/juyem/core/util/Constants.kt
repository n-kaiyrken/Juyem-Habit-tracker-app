package kz.nkaiyrken.juyem.core.util

object Constants {
    // Habit
    const val MAX_HABIT_TITLE_LENGTH = 100
    const val MIN_HABIT_TITLE_LENGTH = 3
    const val DEFAULT_STREAK_THRESHOLD = 3

    // Timer
    const val TIMER_TICK_INTERVAL_MS = 1000L
    const val MAX_TIMER_DURATION_SECONDS = 86400 // 24 hours

    // Notifications
    const val NOTIFICATION_CHANNEL_ID = "habit_reminders"
    const val NOTIFICATION_CHANNEL_NAME = "Напоминания о привычках"
}