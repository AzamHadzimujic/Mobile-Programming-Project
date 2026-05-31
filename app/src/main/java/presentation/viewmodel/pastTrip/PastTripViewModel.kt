package presentation.viewmodel.pastTrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.data.local.entity.PastTripEntity
import model.repository.TripsRepository
import javax.inject.Inject

@HiltViewModel
class PastTripViewModel @Inject constructor(
    private val repository: TripsRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<PastTripUiState>(PastTripUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private var searchQuery = ""
    init {
        loadTrips()
    }
    private fun loadTrips() {
        viewModelScope.launch {
            try {
                val trips = repository.getTripsFromApi()
                publishState(trips)
            } catch (e: Exception) {
                _uiState.value = PastTripUiState.Error(
                    e.message ?: "Failed to load trips"
                )
            }
        }
    }
    fun updateSearch(query: String) {
        searchQuery = query
        loadTrips()
    }
    fun addTrip(
        title: String,
        category: String,
        amount: String,
        date: String
    ) {
        viewModelScope.launch {
            try {
                repository.addTripToApi(
                    title,
                    category,
                    amount,
                    date
                )
                loadTrips()
            } catch (e: Exception) {
                _uiState.value = PastTripUiState.Error(
                    e.message ?: "Failed to add trip"
                )
            }
        }
    }
    fun deleteTrip(trip: PastTripEntity) {
        viewModelScope.launch {
            try {
                repository.deleteTripFromApi(trip.id)
                loadTrips()
            } catch (e: Exception) {
                _uiState.value = PastTripUiState.Error(
                    e.message ?: "Failed to delete trip"
                )
            }
        }
    }
    fun updateTrip(
        id: Int,
        title: String,
        category: String,
        amount: String,
        date: String
    ) {
        viewModelScope.launch {
            try {
                repository.updateTripToApi(
                    id,
                    title,
                    category,
                    amount,
                    date
                )
                loadTrips() // refresh UI
            } catch (e: Exception) {
                _uiState.value = PastTripUiState.Error(
                    e.message ?: "Failed to update trip"
                )
            }
        }
    }
    fun clearAll() {
        viewModelScope.launch {
            try {
                val trips = repository.getTripsFromApi()
                trips.forEach {
                    repository.deleteTripFromApi(it.id)
                }
                loadTrips()
            } catch (e: Exception) {
                _uiState.value = PastTripUiState.Error(
                    e.message ?: "Failed to clear trips"
                )
            }
        }
    }
    private fun publishState(trips: List<PastTripEntity>) {
        val filtered = trips.filter {
            it.title.contains(searchQuery, ignoreCase = true)
        }
        _uiState.value = PastTripUiState.Success(
            searchQuery = searchQuery,
            pastTrips = filtered
        )
    }
}