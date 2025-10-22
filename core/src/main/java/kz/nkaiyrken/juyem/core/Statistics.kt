package kz.nkaiyrken.juyem.core

data class Statistics(
    val habitId: Int,
    val streakCurrent: Int = 0,
    val streakMax: Int = 0,
    val completedTotal: Int = 0,
    val skippedTotal: Int = 0,
    val failedTotal: Int = 0,
    val maxValue: Int? = null,
    val minValue: Int? = null,
    val totalValue: Int = 0
) {
    val completionRate: Float
        get() {
            val total = completedTotal + skippedTotal + failedTotal
            return if (total > 0) {
                (completedTotal.toFloat() / total) * 100
            } else {
                0f
            }
        }
}