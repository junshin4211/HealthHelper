package com.example.healthhelper.ui.theme

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 定義符合您 App 風格的顏色
private val AppOrange = Color(0xFFF39C12)
private val AppLightBackground = Color(0xFFFFFBEF)
private val AppDarkText = Color(0xFF333333)
private val AppOrangeText = Color(0xFFD35400) // 用於卡片中的橘色文字，增加對比

private val DarkColorScheme = darkColorScheme(
    primary = AppOrange,           // 主要顏色，用於 TopBar, 主要按鈕等
    onPrimary = Color.Black,       // 在主要顏色上的文字/圖示顏色
    background = AppLightBackground, // 畫面背景色
    onBackground = AppDarkText,    // 在背景上的文字顏色
    surface = Color.White,         // 卡片、Surface 的背景色
    onSurface = AppDarkText,       // 在 Surface 上的文字顏色
    secondary = AppOrangeText,     // 次要顏色，用於卡片中的文字
    onSecondary = Color.White
)

// M3 的淺色配色方案
private val LightColorScheme = lightColorScheme(
    primary = AppOrange,           // 主要顏色，用於 TopBar, 主要按鈕等
    onPrimary = Color.Black,       // 在主要顏色上的文字/圖示顏色
    background = AppLightBackground, // 畫面背景色
    onBackground = AppDarkText,    // 在背景上的文字顏色
    surface = Color.White,         // 卡片、Surface 的背景色
    onSurface = AppDarkText,       // 在 Surface 上的文字顏色
    secondary = AppOrangeText,     // 次要顏色，用於卡片中的文字
    onSecondary = Color.White
)

@Composable
fun HealthHelperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) { // isInEditMode is true for Previews
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}