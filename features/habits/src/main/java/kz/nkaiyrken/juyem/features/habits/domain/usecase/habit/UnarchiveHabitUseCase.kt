package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitRepository
import java.time.LocalDateTime
import javax.inject.Inject

class UnarchiveHabitUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.unarchiveHabit(id)
    }
}
