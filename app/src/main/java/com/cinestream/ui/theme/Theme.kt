package com.cinestream.ui.theme

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cinestream.R

private val BrandFont = FontFamily(
    Font(R.font.momo_regular, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.cairo_regular, FontWeight.Bold)
)

private val BrandTypography = Typography(
    displayLarge = Typography().displayLarge.copy(fontFamily = BrandFont),
    displayMedium = Typography().displayMedium.copy(fontFamily = BrandFont),
    displaySmall = Typography().displaySmall.copy(fontFamily = BrandFont),
    headlineLarge = Typography().headlineLarge.copy(fontFamily = BrandFont),
    headlineMedium = Typography().headlineMedium.copy(fontFamily = BrandFont),
    headlineSmall = Typography().headlineSmall.copy(fontFamily = BrandFont),
    titleLarge = Typography().titleLarge.copy(fontFamily = BrandFont, fontWeight = FontWeight.SemiBold),
    titleMedium = Typography().titleMedium.copy(fontFamily = BrandFont, fontWeight = FontWeight.Medium),
    titleSmall = Typography().titleSmall.copy(fontFamily = BrandFont),
    bodyLarge = Typography().bodyLarge.copy(fontFamily = BrandFont),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = BrandFont),
    bodySmall = Typography().bodySmall.copy(fontFamily = BrandFont),
    labelLarge = Typography().labelLarge.copy(fontFamily = BrandFont, fontWeight = FontWeight.SemiBold),
    labelMedium = Typography().labelMedium.copy(fontFamily = BrandFont),
    labelSmall = Typography().labelSmall.copy(fontFamily = BrandFont)
)

private val DarkPalette = darkColorScheme(
    primary = Aurora,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = Velvet,
    onPrimaryContainer = Mist,
    secondary = CoralPulse,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    tertiary = NeonMint,
    onTertiary = DeepSpace,
    background = Midnight,
    onBackground = Mist,
    surface = SurfaceElevated,
    onSurface = Mist,
    surfaceVariant = SurfaceBrushed,
    onSurfaceVariant = IcedSlate,
    outline = IcedSlate,
    outlineVariant = OverlayLight,
    inverseSurface = Mist,
    inverseOnSurface = Midnight,
    scrim = OverlayDark
)

private val LightPalette = lightColorScheme(
    primary = Aurora,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = NeonMint,
    onPrimaryContainer = DeepSpace,
    secondary = CoralPulse,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    tertiary = AmberGlow,
    onTertiary = DeepSpace,
    background = androidx.compose.ui.graphics.Color(0xFFF7F7FB),
    onBackground = androidx.compose.ui.graphics.Color(0xFF0F172A),
    surface = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    onSurface = androidx.compose.ui.graphics.Color(0xFF111827),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFE7E9F2),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF475569),
    outline = androidx.compose.ui.graphics.Color(0xFF94A3B8)
)

private val BrandShapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(28.dp),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(40.dp)
)

@Composable
fun CineStreamTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val palette = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkPalette
        else -> LightPalette
    }

    val animatedPalette = palette.copy(
        background = animateColorAsState(palette.background).value,
        surface = animateColorAsState(palette.surface).value
    )

    MaterialTheme(
        colorScheme = animatedPalette,
        typography = BrandTypography,
        shapes = BrandShapes,
        content = content
    )
}



