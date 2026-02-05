package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitRepository
import javax.inject.Inject

class UpdateHabitsOrderUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habits: List<Habit>) {
        repository.updateHabitsOrder(habits)
    }
}
