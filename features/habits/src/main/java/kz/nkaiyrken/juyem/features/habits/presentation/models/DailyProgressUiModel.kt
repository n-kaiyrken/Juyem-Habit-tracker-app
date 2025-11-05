package kz.nkaiyrken.juyem.features.habits.presentation.models

import kz.nkaiyrken.juyem.core.DailyProgressStatus
import java.time.LocalDate

/**
 * Sealed class representing daily progress for different habit types
 *
 * isEnabled flag indicates if the day chip should be clickable:
 * - false: Day before habit creation OR day not in habit's daysOfWeek schedule
 * - true: Valid day that user can interact with
 */
sealed class DailyProgressUiModel {
    abstract val habitId: Int
    abstract val date: LocalDate
    abstract val status: DailyProgressStatus
    abstract val isEnabled: Boolean

    data class TypeBoolean(
        override val habitId: Int,
        override val date: LocalDate,
        override val status: DailyProgressStatus,
        override val isEnabled: Boolean = true,
    ) : DailyProgressUiModel()

    data class Counter(
        override val habitId: Int,
        override val date: LocalDate,
        override val status: DailyProgressStatus,
        override val currentValue: Int,
        override val goalValue: Int,
        val unit: String,
        override val isEnabled: Boolean = true,
    ) : DailyProgressUiModel(), NumericProgress

    data class Timer(
        override val habitId: Int,
        override val date: LocalDate,
        override val status: DailyProgressStatus,
        override val currentValue: Int,
        override val goalValue: Int,
        override val isEnabled: Boolean = true,
    ) : DailyProgressUiModel(), NumericProgress
}

/**
 * Marker interface for progress types that have numeric values (Counter and Timer)
 * Used to make HabitCardProgress type-safe
 */
sealed interface NumericProgress {
    val currentValue: Int
    val goalValue: Int
}