package presentation.viewmodel.pastTrip

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.PastTripItem

@HiltViewModel
class PastTripViewModel @Inject constructor() : ViewModel() {

    private val _pastTrips = MutableStateFlow(
        listOf(
            PastTripItem("1", "Italy Trip", "Food, Culture", "250", "March 2022"),
            PastTripItem("2", "Germany Trip", "History, Nature", "300", "May 2023"),
            PastTripItem("3", "Japan Trip", "Culture, History", "680", "June 2021"),
            PastTripItem("4", "Brazil Trip", "Food, Nature", "740", "January 2026"),
        )
    )

    val pastTrips = _pastTrips.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _showAll = MutableStateFlow(true)
    val showAll = _showAll.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun toggleShowAll() {
        _showAll.value = !_showAll.value
    }

    fun addTrip(title: String) {

        val newId = (_pastTrips.value.size + 1).toString()

        _pastTrips.value += PastTripItem(
                    id = newId,
                    title = title,
                    categories = "New",
                    amount = "0",
                    date = "Now"
                )
    }

    fun deleteTrip(id: String) {
        _pastTrips.value = _pastTrips.value.filter {
            it.id != id
        }
    }
    fun clearAll() {
        _pastTrips.value = emptyList()
    }
}