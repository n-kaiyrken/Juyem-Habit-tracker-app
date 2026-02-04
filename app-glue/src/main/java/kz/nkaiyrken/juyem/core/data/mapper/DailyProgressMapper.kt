package kz.nkaiyrken.juyem.core.data.mapper

import kz.nkaiyrken.database.local.entity.DailyProgressEntity
import kz.nkaiyrken.database.local.entity.DailyProgressStatusEntity
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import javax.inject.Inject

class DailyProgressMapper @Inject constructor() {

    fun toDomain(entity: DailyProgressEntity): DailyProgress = DailyProgress(
        habitId = entity.habitId,
        date = entity.date,
        value = entity.value,
        status = entity.status.toDomain(),
        wasScheduled = entity.wasScheduled
    )

    fun toEntity(domain: DailyProgress): DailyProgressEntity = DailyProgressEntity(
        habitId = domain.habitId,
        date = domain.date,
        value = domain.value,
        status = domain.status.toEntity(),
        wasScheduled = domain.wasScheduled
    )

    fun toDomainList(entities: List<DailyProgressEntity>): List<DailyProgress> =
        entities.map { toDomain(it) }

    fun toEntityList(domains: List<DailyProgress>): List<DailyProgressEntity> =
        domains.map { toEntity(it) }

    // ========== Enum Converters ==========

    private fun DailyProgressStatusEntity.toDomain(): DailyProgressStatus = when (this) {
        DailyProgressStatusEntity.COMPLETED -> DailyProgressStatus.COMPLETED
        DailyProgressStatusEntity.PARTIAL -> DailyProgressStatus.PARTIAL
        DailyProgressStatusEntity.SKIPPED -> DailyProgressStatus.SKIPPED
        DailyProgressStatusEntity.EMPTY -> DailyProgressStatus.EMPTY
        DailyProgressStatusEntity.FAILED -> DailyProgressStatus.FAILED
    }

    private fun DailyProgressStatus.toEntity(): DailyProgressStatusEntity = when (this) {
        DailyProgressStatus.COMPLETED -> DailyProgressStatusEntity.COMPLETED
        DailyProgressStatus.PARTIAL -> DailyProgressStatusEntity.PARTIAL
        DailyProgressStatus.SKIPPED -> DailyProgressStatusEntity.SKIPPED
        DailyProgressStatus.EMPTY -> DailyProgressStatusEntity.EMPTY
        DailyProgressStatus.FAILED -> DailyProgressStatusEntity.FAILED
    }
}
