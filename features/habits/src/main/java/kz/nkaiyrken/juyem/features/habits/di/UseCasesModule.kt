package kz.nkaiyrken.juyem.features.habits.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kz.nkaiyrken.juyem.core.domain.repository.DailyProgressRepository
import kz.nkaiyrken.juyem.core.domain.repository.HabitRepository
import kz.nkaiyrken.juyem.core.domain.usecase.habit.*
import kz.nkaiyrken.juyem.core.domain.usecase.progress.*

/**
 * Hilt модуль для предоставления Use Cases в features:habits модуле.
 *
 * Почему здесь, а не в core:
 * - core модуль должен быть чистым (без зависимостей от фреймворка)
 * - Use Cases не имеют @Inject аннотаций
 * - Dependency Injection настраивается на уровне feature модуля
 *
 * @InstallIn(ViewModelComponent::class):
 * - Use Cases будут доступны для внедрения в ViewModels
 * - Новый инстанс Use Case для каждого ViewModel
 *
 * @ViewModelScoped:
 * - Use Case живет столько же, сколько и ViewModel
 * - Один инстанс на один ViewModel
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    /**
     * Предоставляет GetActiveHabitsUseCase.
     *
     * @param repository будет автоматически внедрен Hilt (из DataModule)
     */
    @Provides
    @ViewModelScoped
    fun provideGetActiveHabitsUseCase(
        repository: HabitRepository
    ): GetActiveHabitsUseCase {
        return GetActiveHabitsUseCase(repository)
    }

    /**
     * Предоставляет GetWeeklyProgressUseCase.
     *
     * @param repository будет автоматически внедрен Hilt (из DataModule)
     */
    @Provides
    @ViewModelScoped
    fun provideGetWeeklyProgressUseCase(
        repository: DailyProgressRepository
    ): GetWeeklyProgressUseCase {
        return GetWeeklyProgressUseCase(repository)
    }

    // ========== Progress Use Cases ==========

    @Provides
    @ViewModelScoped
    fun provideUpdateProgressStatusUseCase(
        repository: DailyProgressRepository
    ): UpdateProgressStatusUseCase {
        return UpdateProgressStatusUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateProgressValueUseCase(
        repository: DailyProgressRepository
    ): UpdateProgressValueUseCase {
        return UpdateProgressValueUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpsertDailyProgressUseCase(
        repository: DailyProgressRepository
    ): UpsertDailyProgressUseCase {
        return UpsertDailyProgressUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetProgressForDateUseCase(
        repository: DailyProgressRepository
    ): GetProgressForDateUseCase {
        return GetProgressForDateUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideAutoMarkFailedDaysUseCase(
        habitRepository: HabitRepository,
        progressRepository: DailyProgressRepository
    ): AutoMarkFailedDaysUseCase {
        return AutoMarkFailedDaysUseCase(habitRepository, progressRepository)
    }

    // ========== Habit Use Cases ==========

    @Provides
    @ViewModelScoped
    fun provideGetHabitByIdUseCase(
        repository: HabitRepository
    ): GetHabitByIdUseCase {
        return GetHabitByIdUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideCreateHabitUseCase(
        repository: HabitRepository
    ): CreateHabitUseCase {
        return CreateHabitUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateHabitUseCase(
        repository: HabitRepository
    ): UpdateHabitUseCase {
        return UpdateHabitUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteHabitUseCase(
        repository: HabitRepository
    ): DeleteHabitUseCase {
        return DeleteHabitUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideArchiveHabitUseCase(
        repository: HabitRepository
    ): ArchiveHabitUseCase {
        return ArchiveHabitUseCase(repository)
    }
}
