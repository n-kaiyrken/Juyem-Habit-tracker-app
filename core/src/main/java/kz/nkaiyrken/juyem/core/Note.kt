package kz.nkaiyrken.juyem.core

import java.time.LocalDate
import java.time.LocalDateTime

data class Note(
    val id: Int = 0,
    val habitId: Int,
    val date: LocalDate,
    val text: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)