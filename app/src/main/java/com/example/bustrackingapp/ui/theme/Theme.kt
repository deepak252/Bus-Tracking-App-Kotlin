package com.example.bustrackingapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(

    primary = NavyBlue700, // Status bar, Button, TF-Label
    onPrimary = White, // Text on Button
    primaryContainer = NavyBlue700,
//    onPrimaryContainer = White,
//    inversePrimary = Blue500,
    secondary = NavyBlue900, //
    onSecondary = White, // Text Color (on Screen),
    secondaryContainer = Blue700, // Bottom Navigation Indicator, CustomButton
    // {CustomSolidButton}
//    onSecondaryContainer = White,
    tertiary = NavyBlue500, // BusTileCard,
    onTertiary = White,
//    onTertiaryContainer = White,
    background = NavyBlue900, // Screen Background
    onBackground = White,  //
    surface = NavyBlue900, //appbar, bottom navigation,
    onSurface = White, //
//    surfaceVariant = Blue500,
    onSurfaceVariant = White, //
    surfaceTint = Blue500, //
//    inverseSurface = Blue500,
//    inverseOnSurface = Blue500,
//    error = error,
//    onError = onError,
//    errorContainer = errorContainer,
//    onErrorContainer = onErrorContainer,
//    outline = Blue500,
//    outlineVariant = Blue500,
//    scrim = Blue500,


)

private val LightColorScheme = lightColorScheme(

    onPrimary = NavyBlue900,

    primary = White,
    secondary = White,
    tertiary = White,
    onTertiary = NavyBlue900,

    primaryContainer = White,

    secondaryContainer = Blue700,
    onSecondaryContainer = White,

    surfaceTint = Blue500, //


//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun BusTrackingAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}