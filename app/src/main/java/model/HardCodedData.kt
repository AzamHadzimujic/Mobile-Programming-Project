package model
//hardcoded data
import androidx.compose.runtime.Stable

@Stable
data class ExpenseItem(
    val id: String,
    val title: String,
    val categories: String,
    val amount: String
)

@Stable
data class PastTripItem(
    val id: String,
    val title: String,
    val categories: String,
    val amount: String,
    val date: String
)

@Stable
data class ScreenShortcutData(
    val title: String,
    val route: String
)