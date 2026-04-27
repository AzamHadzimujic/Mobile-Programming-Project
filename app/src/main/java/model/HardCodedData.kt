package model
//hardcoded data
import androidx.compose.runtime.Stable

@Stable
data class ExpenseItem(
    val id: Int,
    val title: String,
    val isPaid: Boolean = false,
    val categories: List<String>,
    val amount: Int
)

@Stable
data class PastTripItem(
    val id: Int,
    val title: String,
    val isPaid: Boolean = false,
    val categories: List<String>,
    val amount: Int,
    val date: String
)