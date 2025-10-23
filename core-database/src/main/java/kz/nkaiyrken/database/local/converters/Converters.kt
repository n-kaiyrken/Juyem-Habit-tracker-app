package kz.nkaiyrken.database.local.converters

import androidx.room.TypeConverter
import kz.nkaiyrken.database.local.entity.DailyProgressStatusEntity
import kz.nkaiyrken.database.local.entity.HabitStatusEntity
import kz.nkaiyrken.database.local.entity.HabitTypeEntity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.collections.joinToString

class Converters {

    // LocalDateTime converters
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(dateTimeFormatter)
    }

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, dateTimeFormatter) }
    }

    // LocalDate converters
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.format(dateFormatter)
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, dateFormatter) }
    }

    // LocalTime converters
    private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? {
        return value?.format(timeFormatter)
    }

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it, timeFormatter) }
    }

    // Set<DayOfWeek> converter для days_of_week
    @TypeConverter
    fun fromDayOfWeekSet(value: Set<DayOfWeek>?): String? {
        return value?.joinToString(",") { it.name } // "MONDAY,TUESDAY,FRIDAY"
    }

    @TypeConverter
    fun toDayOfWeekSet(value: String?): Set<DayOfWeek>? {
        return value?.split(",")?.mapNotNull {
            try { DayOfWeek.valueOf(it) } catch (e: Exception) { null }
        }?.toSet()
    }

    // ENUM converters
    @TypeConverter
    fun fromHabitType(value: HabitTypeEntity): String = value.name

    @TypeConverter
    fun toHabitType(value: String): HabitTypeEntity = HabitTypeEntity.valueOf(value)

    @TypeConverter
    fun fromHabitStatus(value: HabitStatusEntity): String = value.name

    @TypeConverter
    fun toHabitStatus(value: String): HabitStatusEntity = HabitStatusEntity.valueOf(value)

    @TypeConverter
    fun fromDailyProgressStatus(value: DailyProgressStatusEntity): String = value.name

    @TypeConverter
    fun toDailyProgressStatus(value: String): DailyProgressStatusEntity = DailyProgressStatusEntity.valueOf(value)
}