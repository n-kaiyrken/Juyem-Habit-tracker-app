package kz.nkaiyrken.juyem.core.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.Habit
import java.time.LocalDateTime

/**
 * Repository интерфейс для работы с привычками.
 *
 * Следует принципам Clean Architecture - интерфейс находится в domain слое,
 * а реализация в data слое (core-data модуль).
 */
interface HabitRepository {

    /**
     * Получить список активных привычек (status = ACTIVE).
     * Отсортированы по orderIndex.
     *
     * @return Flow со списком активных привычек (реактивный - обновляется при изменениях в БД)
     */
    fun getActiveHabits(): Flow<List<Habit>>

    /**
     * Получить привычку по ID.
     *
     * @param id уникальный идентификатор привычки
     * @return Flow с привычкой или null если не найдена
     */
    fun getHabitById(id: Int): Flow<Habit?>

    /**
     * Получить архивированные привычки (status = ARCHIVED).
     *
     * @return Flow со списком архивированных привычек
     */
    fun getArchivedHabits(): Flow<List<Habit>>

    /**
     * Создать новую привычку.
     *
     * @param habit новая привычка (id будет автоматически сгенерирован)
     * @return ID созданной привычки
     */
    suspend fun insertHabit(habit: Habit): Long

    /**
     * Обновить существующую привычку.
     *
     * @param habit привычка с обновленными данными
     */
    suspend fun updateHabit(habit: Habit)

    /**
     * Удалить привычку по ID.
     * Удаляет также все связанные данные (прогресс, статистику) благодаря CASCADE в БД.
     *
     * @param id идентификатор привычки
     */
    suspend fun deleteHabit(id: Int)

    /**
     * Архивировать привычку (перевести в статус ARCHIVED).
     *
     * @param id идентификатор привычки
     * @param archivedAt время архивации (по умолчанию текущее)
     */
    suspend fun archiveHabit(id: Int, archivedAt: LocalDateTime = LocalDateTime.now())

    /**
     * Восстановить привычку из архива (перевести в статус ACTIVE).
     *
     * @param id идентификатор привычки
     */
    suspend fun unarchiveHabit(id: Int)

    /**
     * Обновить порядок привычек (для drag-and-drop).
     *
     * @param habits список привычек с обновленными orderIndex
     */
    suspend fun updateHabitsOrder(habits: List<Habit>)
}
