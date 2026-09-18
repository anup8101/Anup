package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme =
  lightColorScheme(
    primary = DeepBluePrimary,
    onPrimary = Color.White,
    primaryContainer = DeepBlueContainer,
    onPrimaryContainer = OnDeepBlueContainer,
    secondary = AccentYellow,
    onSecondary = DeepBlueDark,
    secondaryContainer = AccentYellowLight,
    onSecondaryContainer = AccentYellowDark,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceSubtle,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
  )

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(colorScheme = LightColorScheme, typography = Typography, content = content)
}
