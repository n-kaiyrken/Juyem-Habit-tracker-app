package kz.nkaiyrken.juyem.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.nkaiyrken.database.local.dao.DailyProgressDao
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.data.mapper.DailyProgressMapper
import kz.nkaiyrken.juyem.core.domain.repository.DailyProgressRepository
import java.time.LocalDate
import javax.inject.Inject

class DefaultDailyProgressRepository @Inject constructor(
    private val dailyProgressDao: DailyProgressDao,
    private val mapper: DailyProgressMapper
) : DailyProgressRepository {

    override fun getDailyProgress(habitId: Int, date: LocalDate): Flow<DailyProgress?> =
        dailyProgressDao.getProgress(habitId, date)
            .map { entity -> entity?.let { mapper.toDomain(it) } }

    override fun getWeeklyProgress(habitId: Int, startDate: LocalDate): Flow<List<DailyProgress>> {
        val endDate = startDate.plusDays(6)
        return dailyProgressDao.getProgressRange(habitId, startDate, endDate)
            .map { entities -> mapper.toDomainList(entities) }
    }

    override fun getAllProgressForDate(date: LocalDate): Flow<Map<Int, DailyProgress>> =
        dailyProgressDao.getAllProgressForDate(date)
            .map { entities ->
                entities.associate { entity ->
                    entity.habitId to mapper.toDomain(entity)
                }
            }

    override fun getProgressForDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<Map<Int, Map<LocalDate, DailyProgress>>> =
        dailyProgressDao.getAllProgressForDateRange(startDate, endDate)
            .map { entities ->
                entities
                    .groupBy { it.habitId }
                    .mapValues { (_, progressList) ->
                        progressList.associate { entity ->
                            entity.date to mapper.toDomain(entity)
                        }
                    }
            }

    override suspend fun upsertProgress(progress: DailyProgress) {
        val entity = mapper.toEntity(progress)
        dailyProgressDao.upsert(entity)
    }

    override suspend fun deleteProgress(habitId: Int, date: LocalDate) {
        dailyProgressDao.deleteProgress(habitId, date)
    }

    override suspend fun deleteAllProgressForHabit(habitId: Int) {
        dailyProgressDao.deleteAllProgressForHabit(habitId)
    }
}
