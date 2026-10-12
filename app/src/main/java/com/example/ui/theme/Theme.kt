package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AgriGreenPrimaryDark,
    onPrimary = Color(0xFF00391A),
    primaryContainer = Color(0xFF1E5128),
    onPrimaryContainer = AgriGreenContainer,
    secondary = HarvestGold,
    onSecondary = Color(0xFF3F2E00),
    secondaryContainer = Color(0xFF5B4300),
    onSecondaryContainer = HarvestGoldContainer,
    tertiary = AgriSaffron,
    background = AgriBackgroundDark,
    surface = AgriSurfaceDark,
    surfaceVariant = AgriSurfaceVariantDark,
    onBackground = AgriTextPrimaryDark,
    onSurface = AgriTextPrimaryDark,
    onSurfaceVariant = AgriTextSecondaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = AgriGreenPrimaryLight,
    onPrimary = Color.White,
    primaryContainer = AgriGreenContainer,
    onPrimaryContainer = AgriOnGreenContainer,
    secondary = HarvestGoldDark,
    onSecondary = Color.White,
    secondaryContainer = HarvestGoldContainer,
    onSecondaryContainer = Color(0xFF451A03),
    tertiary = AgriSaffron,
    background = AgriBackgroundLight,
    surface = AgriSurfaceLight,
    surfaceVariant = AgriSurfaceVariantLight,
    onBackground = AgriTextPrimaryLight,
    onSurface = AgriTextPrimaryLight,
    onSurfaceVariant = AgriTextSecondaryLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Prefer our branded agri theme
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
