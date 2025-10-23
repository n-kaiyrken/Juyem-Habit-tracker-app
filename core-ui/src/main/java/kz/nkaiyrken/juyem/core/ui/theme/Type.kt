package kz.nkaiyrken.juyem.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kz.nkaiyrken.juyem.core.ui.R

val JuyemFontFamily = FontFamily(
    Font(R.font.pt_root_ui_regular, FontWeight.Normal),
    Font(R.font.pt_root_ui_light, FontWeight.Light),
    Font(R.font.pt_root_ui_medium, FontWeight.Medium),
    Font(R.font.pt_root_ui_bold, FontWeight.Bold),
)

val JuyemTypography = Typography(
    // Heading 1
    displayLarge = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,     // 700
        fontSize = 43.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.03.sp
    ),

    // Heading 2
    displayMedium = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 37.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.03.sp
    ),

    // Heading 3
    displaySmall = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.03.sp
    ),

    // Heading 4
    headlineLarge = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 27.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.02.sp
    ),

    // Heading 5
    headlineMedium = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.02.sp
    ),

    // Title Bold
    headlineSmall = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.01.sp
    ),

    // Title Regular
    titleLarge = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Normal,  // 400
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.01.sp
    ),

    // Body 1 Bold
    titleMedium = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.01.sp
    ),

    // Body 1 Regular
    bodyLarge = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.01.sp
    ),

    // Body 2 Bold
    titleSmall = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.01.sp
    ),

    // Body 2 Regular
    bodyMedium = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.01.sp
    ),

    // Caption Bold
    labelLarge = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.01.sp
    ),

    // Caption Regular
    bodySmall = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.01.sp
    ),

    labelMedium = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.01.sp
    ),

    labelSmall = TextStyle(
        fontFamily = JuyemFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.01.sp
    )
)
