package kz.nkaiyrken.juyem.features.habits.domain.usecase.progress

import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.data.repository.DailyProgressRepository
import javax.inject.Inject

class UpsertDailyProgressUseCase @Inject constructor(
    private val progressRepository: DailyProgressRepository
) {
    suspend operator fun invoke(progress: DailyProgress) {
        progressRepository.upsertProgress(progress)
    }
}
