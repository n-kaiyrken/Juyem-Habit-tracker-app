package kz.nkaiyrken.juyem.features.habits.domain.usecase.progress

import kotlinx.coroutines.flow.first
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import kz.nkaiyrken.juyem.core.data.repository.DailyProgressRepository
import java.time.LocalDate
import javax.inject.Inject

class UpdateProgressValueUseCase @Inject constructor(
    private val progressRepository: DailyProgressRepository
) {
    suspend operator fun invoke(
        habitId: Int,
        date: LocalDate,
        value: Int
    ) {
        val currentProgress = progressRepository.getDailyProgress(habitId, date).first()

        val updated = currentProgress?.copy(value = value)
            ?: DailyProgress(
                habitId = habitId,
                date = date,
                value = value,
                status = DailyProgressStatus.EMPTY
            )

        progressRepository.upsertProgress(updated)
    }
}
