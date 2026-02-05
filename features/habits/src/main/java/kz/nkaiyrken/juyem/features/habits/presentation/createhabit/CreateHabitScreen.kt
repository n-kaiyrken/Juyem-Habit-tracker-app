package kz.nkaiyrken.juyem.features.habits.presentation.createhabit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kz.nkaiyrken.juyem.core.HabitType
import kz.nkaiyrken.juyem.core.ui.theme.Gray500
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChip
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChipColors
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChipState
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChipType
import kz.nkaiyrken.juyem.core.ui.widgets.input.CommonInputField
import kz.nkaiyrken.juyem.core.ui.widgets.switcher.Switcher
import kz.nkaiyrken.juyem.core.ui.widgets.toolbar.TopAppBarState
import kz.nkaiyrken.juyem.features.habits.R
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun CreateHabitScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateHabitViewModel = hiltViewModel(),
    topAppBarState: TopAppBarState,
    onNavigateBack: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            onNavigateBack()

            // При сохранении надо показать snackbar с тем что привычка создана.
        }
    }

    LaunchedEffect(Unit) {
        topAppBarState.update(
            title = "Новая задача",
            showNavigationIcon = true,
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onNavigateBack,
            actionText = "Сохранить",
            onActionTextClick = viewModel::onSave,
            actionIcon = null
        )
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.additionalColors.backgroundPrimary)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        //Spacer(modifier = Modifier.height(16.dp))

        TitleSection(
            title = uiState.title,
            titleError = uiState.titleError,
            onTitleChange = viewModel::onTitleChange,
        )
        // Здесь 36 dp
        Spacer(modifier = Modifier.height(24.dp))

        CompletionConditionSection(
            selectedType = uiState.habitType,
            onTypeSelected = viewModel::onHabitTypeChange,
        )

        if (uiState.habitType != HabitType.BOOLEAN) {
            // Here 12 dp
            Spacer(modifier = Modifier.height(16.dp))

            GoalSection(
                habitType = uiState.habitType,
                goalValue = uiState.goalValue,
                goalValueError = uiState.goalValueError,
                maxValue = uiState.maxGoalValue,
                selectedUnit = uiState.selectedUnit,
                isDropdownExpanded = uiState.isUnitDropdownExpanded,
                onGoalValueChange = viewModel::onGoalValueChange,
                onUnitSelected = viewModel::onUnitSelected,
                onDropdownToggle = viewModel::onUnitDropdownToggle,
                onDropdownDismiss = viewModel::onUnitDropdownDismiss,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        HabitDaysSection(
            repeatEveryDay = uiState.repeatEveryDay,
            selectedDays = uiState.selectedDays,
            onRepeatEveryDayChange = viewModel::onRepeatEveryDayChange,
            onDayToggle = viewModel::onDayToggle,
        )

        Spacer(modifier = Modifier.height(24.dp))

        ReminderSection(
            reminderEnabled = uiState.reminderEnabled,
            reminderTime = uiState.reminderTime,
            onReminderEnabledChange = viewModel::onReminderEnabledChange,
            onReminderTimeChange = viewModel::onReminderTimeChange,
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun TitleSection(
    title: String,
    titleError: TitleError?,
    onTitleChange: (String) -> Unit,
) {
    val errorText = when (titleError) {
        TitleError.EMPTY -> stringResource(R.string.title_required)
        TitleError.ALREADY_EXISTS -> stringResource(R.string.title_already_exists)
        null -> null
    }

    CommonInputField(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        labelText = stringResource(R.string.title_label),
        onValueChange = onTitleChange,
        errorText = errorText,
        isClearIconVisible = title.isNotEmpty(),
    )
}

@Composable
private fun CompletionConditionSection(
    selectedType: HabitType,
    onTypeSelected: (HabitType) -> Unit,
) {

    // Здесь headlineSmall должен быть
    Text(
        text = stringResource(R.string.completion_condition),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.additionalColors.elementsHighContrast,
    )

    // Here 12 dp space
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        HabitTypeChip(
            text = stringResource(R.string.no_condition),
            isSelected = selectedType == HabitType.BOOLEAN,
            onClick = { onTypeSelected(HabitType.BOOLEAN) },
            modifier = Modifier.weight(1f),
        )
        HabitTypeChip(
            text = stringResource(R.string.quantity_condition),
            isSelected = selectedType == HabitType.COUNTER,
            onClick = { onTypeSelected(HabitType.COUNTER) },
            modifier = Modifier.weight(1f),
        )
        HabitTypeChip(
            text = stringResource(R.string.time_condition),
            isSelected = selectedType == HabitType.TIMER,
            onClick = { onTypeSelected(HabitType.TIMER) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun HabitTypeChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.additionalColors.backgroundDark
    } else {
        Color.Transparent
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.additionalColors.elementsHighContrast,
        )
    }
}

@Composable
private fun GoalSection(
    habitType: HabitType,
    goalValue: String,
    goalValueError: GoalValueError?,
    maxValue: Int,
    selectedUnit: MeasurementUnit?,
    isDropdownExpanded: Boolean,
    onGoalValueChange: (String) -> Unit,
    onUnitSelected: (MeasurementUnit?) -> Unit,
    onDropdownToggle: () -> Unit,
    onDropdownDismiss: () -> Unit,
) {
    val goalErrorText = when (goalValueError) {
        GoalValueError.EMPTY -> stringResource(R.string.goal_required)
        GoalValueError.EXCEEDS_MAX -> stringResource(R.string.max_value_hint, maxValue)
        GoalValueError.INVALID_FORMAT -> null
        null -> null
    }

    CommonInputField(
        modifier = Modifier.fillMaxWidth(),
        text = goalValue,
        labelText = stringResource(R.string.quantity_per_day),
        onValueChange = onGoalValueChange,
        errorText = goalErrorText,
        keyboardType = KeyboardType.Number,
        validSymbols = (0..9).map { it.toString() },
    )

    if (habitType == HabitType.TIMER && goalValue.isNotBlank() && goalValueError == null) {
        Text(
            modifier = Modifier.padding(top = 4.dp, start = 16.dp),
            text = stringResource(R.string.max_value_hint, maxValue),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.additionalColors.elementsLowContrast,
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    UnitDropdown(
        selectedUnit = selectedUnit,
        isExpanded = isDropdownExpanded,
        habitType = habitType,
        onToggle = onDropdownToggle,
        onDismiss = onDropdownDismiss,
        onUnitSelected = onUnitSelected,
    )
}

@Composable
private fun UnitDropdown(
    selectedUnit: MeasurementUnit?,
    isExpanded: Boolean,
    habitType: HabitType,
    onToggle: () -> Unit,
    onDismiss: () -> Unit,
    onUnitSelected: (MeasurementUnit?) -> Unit,
) {
    val units = when (habitType) {
        HabitType.TIMER -> listOf(
            MeasurementUnit.MINUTES,
            MeasurementUnit.HOURS
        )

        HabitType.COUNTER -> listOf(
            null,
            MeasurementUnit.METERS,
            MeasurementUnit.KILOMETERS,
            MeasurementUnit.TIMES,
            MeasurementUnit.GLASSES,
            MeasurementUnit.PAGES,
        )

        else -> emptyList()
    }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.additionalColors.backgroundLight)
                .clickable(onClick = onToggle)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.unit_of_measurement),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.additionalColors.elementsLowContrast,
                )
                Text(
                    text = selectedUnit?.displayName ?: stringResource(R.string.not_selected),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.additionalColors.elementsHighContrast,
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.additionalColors.elementsLowContrast,
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(MaterialTheme.additionalColors.backgroundLight),
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = unit?.displayName ?: stringResource(R.string.not_selected),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.additionalColors.elementsHighContrast,
                        )
                    },
                    onClick = { onUnitSelected(unit) },
                    modifier = Modifier.background(MaterialTheme.additionalColors.backgroundLight),
                )
                if (unit != units.last()) {
                    HorizontalDivider(
                        color = MaterialTheme.additionalColors.elementsLowContrast.copy(alpha = 0.2f),
                    )
                }
            }
        }
    }
}

@Composable
private fun HabitDaysSection(
    repeatEveryDay: Boolean,
    selectedDays: Set<DayOfWeek>,
    onRepeatEveryDayChange: (Boolean) -> Unit,
    onDayToggle: (DayOfWeek) -> Unit,
) {
    Text(
        text = stringResource(R.string.habit_days),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.additionalColors.elementsHighContrast,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.additionalColors.backgroundLight)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.repeat_every_day),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.additionalColors.elementsHighContrast,
            modifier = Modifier.weight(1f),
        )
        Switcher(
            checked = repeatEveryDay,
            onCheckedChange = onRepeatEveryDayChange,
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.additionalColors.backgroundPrimary),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val daysInOrder = listOf(
            DayOfWeek.MONDAY to stringResource(R.string.day_mon),
            DayOfWeek.TUESDAY to stringResource(R.string.day_tue),
            DayOfWeek.WEDNESDAY to stringResource(R.string.day_wed),
            DayOfWeek.THURSDAY to stringResource(R.string.day_thu),
            DayOfWeek.FRIDAY to stringResource(R.string.day_fri),
            DayOfWeek.SATURDAY to stringResource(R.string.day_sat),
            DayOfWeek.SUNDAY to stringResource(R.string.day_sun),
        )

        daysInOrder.forEach { (day, label) ->
            val isSelected = selectedDays.contains(day)
            val colors = if (isSelected) {
                DayChipColors(
                    background = MaterialTheme.additionalColors.backgroundAccentLight2,
                    content =MaterialTheme.additionalColors.elementsAccent
                )
            } else {
                DayChipColors(
                    background = Color.Transparent,
                    content = MaterialTheme.additionalColors.elementsLowContrast
                )
            }
            DayChip(
                dayLabel = label,
                colors = colors,
                onClick = { onDayToggle(day) },
            )
        }
    }
}

@Composable
private fun ReminderSection(
    reminderEnabled: Boolean,
    reminderTime: LocalTime,
    onReminderEnabledChange: (Boolean) -> Unit,
    onReminderTimeChange: (LocalTime) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.additionalColors.backgroundLight)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.enable_reminder),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.additionalColors.elementsHighContrast,
            modifier = Modifier.weight(1f),
        )
        Switcher(
            checked = reminderEnabled,
            onCheckedChange = onReminderEnabledChange,
        )
    }

    if (reminderEnabled) {
        //Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = MaterialTheme.additionalColors.elementsLowContrast.copy(alpha = 0.2f),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp))
                .background(MaterialTheme.additionalColors.backgroundLight)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.additionalColors.elementsAccent,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = reminderTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.additionalColors.elementsHighContrast,
                modifier = Modifier.weight(1f),
            )

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* TODO: Open time picker */ },
                tint = MaterialTheme.additionalColors.elementsLowContrast,
            )
        }
    }
}
