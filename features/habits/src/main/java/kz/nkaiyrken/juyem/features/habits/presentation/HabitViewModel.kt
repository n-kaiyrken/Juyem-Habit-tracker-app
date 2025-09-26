package kz.nkaiyrken.juyem.features.habits.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitsRepository
import kz.nkaiyrken.juyem.features.habits.presentation.HabitUiState.Success
import kz.nkaiyrken.juyem.features.habits.presentation.HabitUiState.Error
import kz.nkaiyrken.juyem.features.habits.presentation.HabitUiState.Loading
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val habitRepository: HabitsRepository
) : ViewModel() {

    val uiState: StateFlow<HabitUiState> = habitRepository
        .habits.map<List<String>, HabitUiState> { Success(data = it) }
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addHabit(name: String) {
        viewModelScope.launch {
            habitRepository.add(name)
        }
    }
}
