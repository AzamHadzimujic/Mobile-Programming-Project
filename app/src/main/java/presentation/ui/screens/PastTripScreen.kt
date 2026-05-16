package presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import model.data.local.entity.PastTripEntity
import presentation.ui.components.SearchBar
import presentation.viewmodel.pastTrip.PastTripUiState
import presentation.viewmodel.pastTrip.PastTripViewModel

@Composable
fun PastTripScreen(
    viewModel: PastTripViewModel,
    onTripClick: () -> Unit
) {
    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()
    when (uiState) {
        is PastTripUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        is PastTripUiState.Error -> {
            val error =
                uiState as PastTripUiState.Error
            Text(
                text = error.message,
                color =
                    MaterialTheme.colorScheme.error
            )
        }
        is PastTripUiState.Success -> {
            val state =
                uiState as PastTripUiState.Success
            PastTripsContent(
                pastTrips =
                    state.pastTrips,
                searchQuery =
                    state.searchQuery,
                onSearchQueryChange =
                    viewModel::updateSearch,
                onDeleteTrip =
                    viewModel::deleteTrip,
                onAddTrip =
                    viewModel::addTrip,
                onClearCompleted =
                    viewModel::clearAll,
                onTripClick =
                    onTripClick
            )
        }
    }
}
@Composable
fun PastTripsContent(
    pastTrips: List<PastTripEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onDeleteTrip: (PastTripEntity) -> Unit,
    onAddTrip: (String) -> Unit,
    onClearCompleted: () -> Unit,
    onTripClick: () -> Unit
) {
    var newTripText by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Past Trips Expenses",
            style =
                MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "${pastTrips.size} trips",
            style =
                MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider()
        SearchBar(
            query = searchQuery,
            onQueryChange =
                onSearchQueryChange
        )
        HorizontalDivider()
        LazyColumn {
            items(
                pastTrips,
                key = { it.id }
            ) { trip ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onTripClick()
                        }
                ) {
                    Row(
                        modifier =
                            Modifier.padding(12.dp),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(trip.title)
                            Text(
                                text = trip.categories,
                                style =
                                    MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text =
                                    "Amount: ${trip.amount}",
                                style =
                                    MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = trip.date,
                                style =
                                    MaterialTheme.typography.bodySmall
                            )
                        }
                        IconButton(
                            onClick = {
                                onDeleteTrip(trip)
                            }
                        ) {
                            Icon(
                                imageVector =
                                    Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )
            }
            if (pastTrips.isEmpty()) {
                item {
                    Text("No trips found")
                }
            }
        }
        HorizontalDivider()
        Row {
            OutlinedTextField(
                value = newTripText,
                onValueChange = {
                    newTripText = it
                },
                modifier =
                    Modifier.weight(1f),
                label = {
                    Text("New Trip")
                }
            )
            Spacer(
                modifier =
                    Modifier.width(8.dp)
            )
            Button(
                onClick = {
                    onAddTrip(newTripText)
                    newTripText = ""
                },
                enabled =
                    newTripText.isNotBlank()
            ) {
                Text("Add")
            }
        }
        Spacer(
            modifier =
                Modifier.height(8.dp)
        )
        OutlinedButton(
            onClick = onClearCompleted,
            modifier =
                Modifier.fillMaxWidth()
        ) {
            Text("Clear All")
        }
    }
}