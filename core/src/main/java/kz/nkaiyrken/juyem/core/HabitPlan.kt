package kz.nkaiyrken.juyem.core

data class HabitPlan(
    val habitId: Int,
    val period: PlanPeriod,
    val targetValue: Int,
    val achievedValue: Int = 0
) {
    val progress: Float
        get() = if (targetValue > 0) {
            (achievedValue.toFloat() / targetValue) * 100
        } else {
            0f
        }

    val isCompleted: Boolean
        get() = achievedValue >= targetValue
}

enum class PlanPeriod {
    DAILY,
    WEEKLY,
    MONTHLY
}