package model

import androidx.compose.runtime.Stable

@Stable
data class ScreenShortcutData(
    val title: String,
    val route: String
)