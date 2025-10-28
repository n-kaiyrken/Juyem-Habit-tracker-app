package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.data.repository.HabitRepository

class CreateHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habit: Habit): Long {
        return repository.insertHabit(habit)
    }
}
