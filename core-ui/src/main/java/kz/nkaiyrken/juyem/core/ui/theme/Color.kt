package kz.nkaiyrken.juyem.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val CoreWhite = Color(0xFFFFFFFF)
val CoreBlack = Color(0xFF000000)

val LightGray100 = Color(0xFFF7F8F9)
val LightGray200 = Color(0xFFEEF0F2)
val LightGray300 = Color(0xFFE2E5E9)
val LightGray400 = Color(0xFFD6DBE0)
val LightGray500 = Color(0xFFCBD1D8)
val LightGray600 = Color(0xFFBFC7CF)
val LightGray700 = Color(0xFFB3BDC6)
val LightGray800 = Color(0xFFA8B2BD)
val LightGray900 = Color(0xFF9CA8B5)
val LightGray950 = Color(0xFF96A3B0)

val Gray100 = Color(0xFFE9EBEC)
val Gray200 = Color(0xFFCDD1D5)
val Gray300 = Color(0xFFB2B8BD)
val Gray400 = Color(0xFF969EA6)
val Gray500 = Color(0xFF7B858E)
val Gray600 = Color(0xFF636B74)
val Gray700 = Color(0xFF4B5258)
val Gray800 = Color(0xFF34383D)
val Gray900 = Color(0xFF1C1F21)
val Gray950 = Color(0xFF101213)

val Green50 = Color(0xFFECF9EB)
val Green100 = Color(0xFFDAF4D7)
val Green200 = Color(0xFFB5E8B0)
val Green300 = Color(0xFF8FDD88)
val Green400 = Color(0xFF6AD161)
val Green500 = Color(0xFF45C639)
val Green600 = Color(0xFF379E2E)
val Green700 = Color(0xFF297722)
val Green800 = Color(0xFF1C4F17)
val Green900 = Color(0xFF0E280B)

val Red50 = Color(0xFFFBE3E0)
val Red100 = Color(0xFFF8CFC9)
val Red200 = Color(0xFFF1A79D)
val Red300 = Color(0xFFEB7F70)
val Red400 = Color(0xFFE55743)
val Red500 = Color(0xFFD7351E)
val Red600 = Color(0xFFAA2A18)
val Red700 = Color(0xFF7D1F11)
val Red800 = Color(0xFF51140B)
val Red900 = Color(0xFF240905)

val Blue100 = Color(0xFFECF5FD)
val Blue150 = Color(0xFFD5E8FC)
val Blue200 = Color(0xFFBDDBFA)
val Blue300 = Color(0xFF8EC1F6)
val Blue400 = Color(0xFF5FA7F2)
val Blue500 = Color(0xFF308DEE)
val Blue600 = Color(0xFF1273D9)
val Blue700 = Color(0xFF0E5AAA)
val Blue800 = Color(0xFF0A417B)
val Blue900 = Color(0xFF06284B)
val Blue950 = Color(0xFF041C34)

data class AdditionalColors(
    val coreWhite: Color,
    val coreBlack: Color,
    val backgroundPrimary: Color,
    val backgroundLight: Color,
    val backgroundDark: Color,
    val backgroundOverlay: Color,
    val backgroundAccent: Color,
    val backgroundAccentLight1: Color,
    val backgroundAccentLight2: Color,
    val backgroundSuccess: Color,
    val backgroundSuccessLight1: Color,
    val backgroundError: Color,
    val backgroundErrorLight1: Color,
    val elementsWhite: Color,
    val elementsLight: Color,
    val elementsLowContrast: Color,
    val elementsHighContrast: Color,
    val elementsAccent: Color,
    val elementsAccentLight3: Color,
    val elementsSuccess: Color,
    val elementsSuccessLight3: Color,
    val elementsError: Color,
    val elementsErrorLight3: Color,
    val shadesAccentDark2: Color,
    val shadesAccentDark3: Color,
    val shadesSuccessDark2: Color,
    val shadesSuccessDark3: Color,
    val shadesErrorDark2: Color,
    val shadesErrorDark3: Color,
    val shadesGray200: Color,
)

val additionalLightColors = AdditionalColors(
    coreWhite = CoreWhite,
    coreBlack = CoreBlack,
    backgroundPrimary = CoreWhite,
    backgroundLight = LightGray100,
    backgroundDark = LightGray200,
    backgroundOverlay = Gray950.copy(alpha = 0.4f),
    backgroundAccent = Blue500,
    backgroundAccentLight1 = Blue100,
    backgroundAccentLight2 = Blue150,
    backgroundSuccess = Green600,
    backgroundSuccessLight1 = Green50,
    backgroundError = Red600,
    backgroundErrorLight1 = Red50,
    elementsWhite = CoreWhite,
    elementsLight = Gray100,
    elementsLowContrast = Gray500,
    elementsHighContrast = Gray900,
    elementsAccent = Blue500,
    elementsAccentLight3 = Blue200,
    elementsSuccess = Green600,
    elementsSuccessLight3 = Green300,
    elementsError = Red600,
    elementsErrorLight3 = Red300,
    shadesAccentDark2 = Blue700,
    shadesAccentDark3 = Blue800,
    shadesSuccessDark2 = Green700,
    shadesSuccessDark3 = Green800,
    shadesErrorDark2 = Red700,
    shadesErrorDark3 = Red800,
    shadesGray200 = Gray200,
)

val additionalDarkColors = AdditionalColors(
    coreWhite = CoreWhite,
    coreBlack = CoreBlack,
    backgroundPrimary = Gray950,
    backgroundLight = Gray900,
    backgroundDark = Gray800,
    backgroundOverlay = CoreBlack.copy(alpha = 0.6f),
    backgroundAccent = Blue600,
    backgroundAccentLight1 = Blue950,
    backgroundAccentLight2 = Blue900,
    backgroundSuccess = Green600,
    backgroundSuccessLight1 = Green900.copy(alpha = 0.3f),
    backgroundError = Red600,
    backgroundErrorLight1 = Red900.copy(alpha = 0.3f),
    elementsWhite = CoreWhite,
    elementsLight = Gray800,
    elementsLowContrast = Gray500,
    elementsHighContrast = Gray100,
    elementsAccent = Blue400,
    elementsAccentLight3 = Blue700,
    elementsSuccess = Green400,
    elementsSuccessLight3 = Green700,
    elementsError = Red400,
    elementsErrorLight3 = Red700,
    shadesAccentDark2 = Blue300,
    shadesAccentDark3 = Blue200,
    shadesSuccessDark2 = Green300,
    shadesSuccessDark3 = Green200,
    shadesErrorDark2 = Red300,
    shadesErrorDark3 = Red200,
    shadesGray200 = Gray100,
)

val LocalAdditionalColors = staticCompositionLocalOf { additionalLightColors }

val MaterialTheme.additionalColors: AdditionalColors
    @Composable
    get() = LocalAdditionalColors.current
