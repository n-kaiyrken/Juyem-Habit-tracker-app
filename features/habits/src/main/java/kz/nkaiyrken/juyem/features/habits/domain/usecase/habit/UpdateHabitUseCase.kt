package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kz.nkaiyrken.juyem.core.Habit

class UpdateHabitUseCase(
    private val repository: kz.nkaiyrken.juyem.core.data.repository.HabitRepository
) {
    suspend operator fun invoke(habit: Habit) {
        repository.updateHabit(habit)
    }
}
