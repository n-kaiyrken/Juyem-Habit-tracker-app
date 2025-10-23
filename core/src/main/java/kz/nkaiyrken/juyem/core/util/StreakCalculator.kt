package kz.nkaiyrken.juyem.core.util

import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import java.time.LocalDate

object StreakCalculator {

    /**
     * Вычисляет текущую серию выполнений
     */
    fun calculateCurrentStreak(progressList: List<DailyProgress>): Int {
        if (progressList.isEmpty()) return 0

        val sortedProgress = progressList
            .sortedByDescending { it.date }

        var streak = 0
        var currentDate = LocalDate.now()

        for (progress in sortedProgress) {
            if (progress.status == DailyProgressStatus.COMPLETED) {
                if (progress.date == currentDate ||
                    progress.date == currentDate.minusDays(1)) {
                    streak++
                    currentDate = progress.date.minusDays(1)
                } else {
                    break
                }
            }
        }

        return streak
    }

    /**
     * Вычисляет максимальную серию
     */
    fun calculateMaxStreak(progressList: List<DailyProgress>): Int {
        if (progressList.isEmpty()) return 0

        val sortedProgress = progressList.sortedBy { it.date }
        var maxStreak = 0
        var currentStreak = 0
        var lastDate: LocalDate? = null

        for (progress in sortedProgress) {
            if (progress.status == DailyProgressStatus.COMPLETED) {
                if (lastDate == null ||
                    DateUtils.daysBetween(lastDate, progress.date) == 1) {
                    currentStreak++
                    maxStreak = maxOf(maxStreak, currentStreak)
                } else {
                    currentStreak = 1
                }
                lastDate = progress.date
            } else {
                currentStreak = 0
                lastDate = null
            }
        }

        return maxStreak
    }

    /**
     * Проверяет, не прервана ли серия
     */
    fun isStreakActive(progressList: List<DailyProgress>): Boolean {
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        return progressList.any {
            (it.date == today || it.date == yesterday) &&
                    it.status == DailyProgressStatus.COMPLETED
        }
    }
}