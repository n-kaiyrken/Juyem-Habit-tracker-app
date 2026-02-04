package kz.nkaiyrken.juyem.features.habits.domain.usecase.progress

import kotlinx.coroutines.flow.first
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import kz.nkaiyrken.juyem.features.habits.domain.repository.DailyProgressRepository
import java.time.LocalDate
import javax.inject.Inject

class UpdateProgressStatusUseCase @Inject constructor(
    private val progressRepository: DailyProgressRepository
) {
    suspend operator fun invoke(
        habitId: Int,
        date: LocalDate,
        status: DailyProgressStatus,
    ) {
        val currentProgress = progressRepository.getDailyProgress(habitId, date).first()

        val updated = currentProgress?.copy(status = status)
            ?: DailyProgress(
                habitId = habitId,
                date = date,
                value = 0,
                status = status
            )

        progressRepository.upsertProgress(updated)
    }
}
