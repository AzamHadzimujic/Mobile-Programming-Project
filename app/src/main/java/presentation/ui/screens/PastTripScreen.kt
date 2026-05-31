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
                onAddTrip = { title, category, amount, date ->
                    viewModel.addTrip(title, category, amount, date)
                },
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
    onAddTrip: (String, String, String, String) -> Unit,
    onClearCompleted: () -> Unit,
    onTripClick: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Past Trips Expenses",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "${pastTrips.size} trips",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange
        )
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pastTrips, key = { it.id }) { trip ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTripClick() }
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
                            Text(
                                text = trip.date,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        IconButton(onClick = { onDeleteTrip(trip) }) {
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
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Title") }
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Category") }
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Amount") }
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Date") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onAddTrip(title,category,amount,date)
                        title = ""
                        category = ""
                        amount = ""
                        date = ""
                    },
                    enabled = title.isNotBlank() &&
                            category.isNotBlank() &&
                            amount.isNotBlank()
                ) {
                    Text("Add Trip")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onClearCompleted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear All")
        }
    }
}