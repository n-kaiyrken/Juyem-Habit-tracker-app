package kz.nkaiyrken.juyem.features.habits.domain.repository

import kotlinx.coroutines.flow.Flow

interface HabitsRepository {
    val habits: Flow<List<String>>

    suspend fun add(name: String)
}

