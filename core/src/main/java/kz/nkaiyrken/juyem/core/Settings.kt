package kz.nkaiyrken.juyem.core

import java.time.LocalDateTime

data class Settings(
    val userId: Int,
    val language: String = "ru",
    val isdarkTheme: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val lastBackupAt: LocalDateTime? = null
)