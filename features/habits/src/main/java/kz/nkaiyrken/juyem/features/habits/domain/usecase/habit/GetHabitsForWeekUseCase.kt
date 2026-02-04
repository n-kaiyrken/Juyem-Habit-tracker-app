package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitRepository
import kz.nkaiyrken.juyem.core.util.DateUtils.getWeekEnd
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class GetHabitsForWeekUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    operator fun invoke(weekStartDate: LocalDate): Flow<List<Habit>> {
        val weekEndDate = getWeekEnd(weekStartDate).atTime(LocalTime.MAX)
        return repository.getActiveHabitsForWeek(weekEndDate)
    }
}
