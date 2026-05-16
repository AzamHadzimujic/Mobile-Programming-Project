package presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import model.PastTripItem
import presentation.ui.components.SearchBar
import presentation.viewmodel.pastTrip.PastTripViewModel

// STATEFUL SCREEN — single source of truth for all state
@Composable
fun PastTripScreen(
    viewModel: PastTripViewModel,
    onTripClick: (String) -> Unit
) {
    val trips by viewModel.pastTrips.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val showAll by viewModel.showAll.collectAsStateWithLifecycle()
    val filteredTrips = remember(trips, searchQuery) {
        if (searchQuery.isBlank()) trips
        else trips.filter {
            it.title.contains(searchQuery, ignoreCase = true)
        }
    }
    PastTripsContent(
        pastTrips = filteredTrips,
        searchQuery = searchQuery,
        showCompleted = showAll,
        completedCount = trips.size,
        totalCount = trips.size,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onShowCompletedToggle = viewModel::toggleShowAll,
        onDeleteTrip = { viewModel.deleteTrip(it.id) },
        onAddTrip = viewModel::addTrip,
        onClearCompleted = viewModel::clearAll,
        onTripClick = onTripClick
    )
}

// ─────────────────────────────────────────────────────────────
// STATELESS CONTENT — owns no state
// ────────────────────────────────────────────────────────────-

@Composable
fun PastTripsContent(
    pastTrips: List<PastTripItem>,
    searchQuery: String,
    showCompleted: Boolean,
    completedCount: Int,
    totalCount: Int,
    onSearchQueryChange: (String) -> Unit,
    onShowCompletedToggle: () -> Unit,
    onDeleteTrip: (PastTripItem) -> Unit,
    onAddTrip: (String) -> Unit,
    onClearCompleted: () -> Unit,
    onTripClick: (String) -> Unit
) {

    var newTripText by remember { mutableStateOf("") }
    Column {
        Text(
            text = "Past Trips Expenses",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "$completedCount / $totalCount trips",
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider()
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange
        )
        Row {
            Checkbox(
                checked = showCompleted,
                onCheckedChange = { onShowCompletedToggle() }
            )
            Text("Show all trips")
        }
        HorizontalDivider()
        LazyColumn {
            items(pastTrips, key = { it.id }) { trip ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTripClick(trip.id) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(trip.title)
                            Text(
                                text = trip.categories,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Amount: ${trip.amount}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        IconButton(
                            onClick = { onDeleteTrip(trip) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
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
                onValueChange = { newTripText = it },
                modifier = Modifier.weight(1f),
                label = { Text("New Trip") }
            )
            Button(
                onClick = {
                    onAddTrip(newTripText)
                    newTripText = ""
                },
                enabled = newTripText.isNotBlank()
            ) {
                Text("Add")
            }
        }
        OutlinedButton(
            onClick = onClearCompleted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear All")
        }
    }
}