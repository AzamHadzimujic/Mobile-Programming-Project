package presentation.viewmodel.pastTrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.data.local.entity.PastTripEntity
import model.repository.pasttrip.PastTripRepository
import javax.inject.Inject

@HiltViewModel
class PastTripViewModel @Inject constructor(
    private val repository: PastTripRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<PastTripUiState>(
            PastTripUiState.Loading
        )
    val uiState =
        _uiState.asStateFlow()
    private var searchQuery = ""
    init {
        observeTrips()
    }
    private fun observeTrips() {
        viewModelScope.launch {
            repository.getAllTrips()
                .collect { trips ->
                    publishState(trips)
                }
        }
    }
    fun updateSearch(query: String) {
        searchQuery = query
        viewModelScope.launch {
            repository.getAllTrips()
                .first()
                .let { publishState(it) }
        }
    }
    fun addTrip(title: String, category: String, amount: String, date: String) {
        viewModelScope.launch {
            repository.insertTrip(
                PastTripEntity(
                    title = title,
                    categories = category,
                    amount = amount,
                    date = date
                )
            )
        }
    }
    fun deleteTrip(
        trip: PastTripEntity
    ) {
        viewModelScope.launch {
            repository.deleteTrip(trip)
        }
    }
    fun clearAll() {
        viewModelScope.launch {
            repository.getAllTrips()
                .first()
                .forEach {
                    repository.deleteTrip(it)
                }
        }
    }
    private fun publishState(
        trips: List<PastTripEntity>
    ) {
        val filtered =
            trips.filter {
                it.title.contains(
                    searchQuery,
                    ignoreCase = true
                )
            }
        _uiState.value =
            PastTripUiState.Success(
                searchQuery = searchQuery,
                pastTrips = filtered
            )
    }
}