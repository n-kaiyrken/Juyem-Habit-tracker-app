package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.data.repository.HabitRepository
import javax.inject.Inject

class GetHabitByIdUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    operator fun invoke(id: Int): Flow<Habit?> {
        return repository.getHabitById(id)
    }
}
