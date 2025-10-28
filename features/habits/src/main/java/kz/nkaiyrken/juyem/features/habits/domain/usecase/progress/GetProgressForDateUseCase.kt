package kz.nkaiyrken.juyem.features.habits.domain.usecase.progress

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.data.repository.DailyProgressRepository
import java.time.LocalDate

class GetProgressForDateUseCase(
    private val progressRepository: DailyProgressRepository
) {
    operator fun invoke(habitId: Int, date: LocalDate): Flow<DailyProgress?> {
        return progressRepository.getDailyProgress(habitId, date)
    }
}
