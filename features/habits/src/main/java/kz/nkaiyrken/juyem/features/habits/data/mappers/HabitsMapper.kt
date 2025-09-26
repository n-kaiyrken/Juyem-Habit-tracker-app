package kz.nkaiyrken.juyem.features.habits.data.mappers

import kz.nkaiyrken.juyem.features.habits.data.local.entity.HabitEntity
import kz.nkaiyrken.juyem.features.habits.domain.model.Habit
import javax.inject.Inject

class HabitsMapper @Inject constructor() {

//  HabitEntity -> Habit
    fun mapHabitEntityToDomain(habitEntity: HabitEntity) = Habit(
        name = habitEntity.name,
    )

//  Habit -> HabitEntity
    fun mapHabitToEntity(habit: Habit) = HabitEntity(
        name = habit.name,
    )
}
//// HabitEntity -> Habit
//fun HabitEntity.toDomain() = Habit(
//    name = name,
//)
//
//// Habit -> HabitEntity
//fun Habit.toEntity() = HabitEntity(
//    name = name,
//)