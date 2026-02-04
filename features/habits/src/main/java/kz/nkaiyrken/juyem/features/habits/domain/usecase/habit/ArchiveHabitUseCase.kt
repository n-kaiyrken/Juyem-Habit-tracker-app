package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitRepository
import java.time.LocalDateTime
import javax.inject.Inject

class ArchiveHabitUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(id: Int, archivedAt: LocalDateTime = LocalDateTime.now()) {
        repository.archiveHabit(id, archivedAt)
    }

    suspend fun unarchive(id: Int) {
        repository.unarchiveHabit(id)
    }
}
