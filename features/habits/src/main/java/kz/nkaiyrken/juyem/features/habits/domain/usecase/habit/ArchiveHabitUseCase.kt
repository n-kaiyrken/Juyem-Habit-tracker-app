package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kz.nkaiyrken.juyem.core.data.repository.HabitRepository
import java.time.LocalDateTime

class ArchiveHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(id: Int, archivedAt: LocalDateTime = LocalDateTime.now()) {
        repository.archiveHabit(id, archivedAt)
    }

    suspend fun unarchive(id: Int) {
        repository.unarchiveHabit(id)
    }
}
