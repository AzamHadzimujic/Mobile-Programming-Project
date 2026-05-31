package model.data.remote.dto

data class PastTripDto(
    val id: Int = 0,
    val title: String,
    val categories: String,
    val amount: String,
    val date: String
)