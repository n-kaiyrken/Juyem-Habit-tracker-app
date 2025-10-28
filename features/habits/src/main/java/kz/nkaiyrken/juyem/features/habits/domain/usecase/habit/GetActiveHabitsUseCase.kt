package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.data.repository.HabitRepository

class GetActiveHabitsUseCase(
    private val repository: HabitRepository
) {
    operator fun invoke(): Flow<List<Habit>> {
        return repository.getActiveHabits()
    }
}
