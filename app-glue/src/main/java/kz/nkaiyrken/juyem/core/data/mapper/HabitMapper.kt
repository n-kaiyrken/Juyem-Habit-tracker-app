package kz.nkaiyrken.juyem.core.data.mapper

import kz.nkaiyrken.database.local.entity.HabitEntity
import kz.nkaiyrken.database.local.entity.HabitStatusEntity
import kz.nkaiyrken.database.local.entity.HabitTypeEntity
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.HabitStatus
import kz.nkaiyrken.juyem.core.HabitType
import javax.inject.Inject

class HabitMapper @Inject constructor() {

    fun toDomain(entity: HabitEntity): Habit = Habit(
        id = entity.habitId,
        title = entity.title,
        orderIndex = entity.orderIndex,
        type = entity.type.toDomain(),
        goalValue = entity.goalValue,
        unit = entity.unit,
        daysOfWeek = entity.daysOfWeek,
        remindEnabled = entity.remindEnabled,
        remindTime = entity.remindTime,
        status = entity.status.toDomain(),
        createdAt = entity.createdAt,
        archivedAt = entity.archivedAt
    )

    fun toEntity(domain: Habit): HabitEntity = HabitEntity(
        habitId = domain.id,
        title = domain.title,
        orderIndex = domain.orderIndex,
        type = domain.type.toEntity(),
        goalValue = domain.goalValue,
        unit = domain.unit,
        daysOfWeek = domain.daysOfWeek,
        remindEnabled = domain.remindEnabled,
        remindTime = domain.remindTime,
        status = domain.status.toEntity(),
        createdAt = domain.createdAt,
        archivedAt = domain.archivedAt
    )

    fun toDomainList(entities: List<HabitEntity>): List<Habit> =
        entities.map { toDomain(it) }

    fun toEntityList(domains: List<Habit>): List<HabitEntity> =
        domains.map { toEntity(it) }

    // ========== Enum Converters ==========

    private fun HabitTypeEntity.toDomain(): HabitType = when (this) {
        HabitTypeEntity.BOOLEAN -> HabitType.BOOLEAN
        HabitTypeEntity.COUNTER -> HabitType.COUNTER
        HabitTypeEntity.TIMER -> HabitType.TIMER
    }

    private fun HabitType.toEntity(): HabitTypeEntity = when (this) {
        HabitType.BOOLEAN -> HabitTypeEntity.BOOLEAN
        HabitType.COUNTER -> HabitTypeEntity.COUNTER
        HabitType.TIMER -> HabitTypeEntity.TIMER
    }

    private fun HabitStatusEntity.toDomain(): HabitStatus = when (this) {
        HabitStatusEntity.ACTIVE -> HabitStatus.ACTIVE
        HabitStatusEntity.ARCHIVED -> HabitStatus.ARCHIVED
    }

    private fun HabitStatus.toEntity(): HabitStatusEntity = when (this) {
        HabitStatus.ACTIVE -> HabitStatusEntity.ACTIVE
        HabitStatus.ARCHIVED -> HabitStatusEntity.ARCHIVED
    }
}
