package kz.nkaiyrken.juyem.features.habits.presentation

import java.time.LocalDate

sealed interface TopBarTitle {
    data object ActiveHabits : TopBarTitle

    data object Today : TopBarTitle

    data object Yesterday : TopBarTitle

    data object Tomorrow : TopBarTitle

    data class CustomDate(val date: LocalDate) : TopBarTitle
}
