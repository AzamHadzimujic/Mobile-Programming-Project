package presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.PastTripItem
import presentation.ui.components.SearchBar

// STATEFUL SCREEN — single source of truth for all state
@Composable
fun PastTripScreen(
    onTripClick: (Int) -> Unit
) {

    // ✅ mutableStateListOf — observable list
    val pastTripItems = remember {
        mutableStateListOf(
            PastTripItem(1, "Italy Trip", false, listOf("Food", "Culture"), 250, "March 2022"),
            PastTripItem(2, "Germany Trip", true, listOf("History", "Nature"), 300, "May 2023"),
            PastTripItem(3, "Japan Trip", true, listOf("Culture", "History"), 680, "June 2021"),
            PastTripItem(4, "Brazil Trip", true, listOf("Food", "Nature"), 740, "January 2026"),
        )
    }

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showCompleted by rememberSaveable { mutableStateOf(true) }

    // ✅ derivedStateOf — only recomposes when result changes
    val filteredPastTrips by remember {
        derivedStateOf {
            pastTripItems.filter { pastTrip ->
                val matchesQuery = pastTrip.title.contains(searchQuery, ignoreCase = true)
                val matchesFilter = if (showCompleted) true else !pastTrip.isPaid
                matchesQuery && matchesFilter
            }
        }
    }

    val completedCount by remember { derivedStateOf { pastTripItems.count { it.isPaid } } }
    val totalCount by remember { derivedStateOf { pastTripItems.size } }
    val allDone by remember { derivedStateOf { pastTripItems.isNotEmpty() && completedCount == totalCount } }

    var nextId by remember { mutableStateOf(pastTripItems.size + 1) }

    // ✅ remember(key) — stable lambda, not recreated on every recomposition
    val onToggleComplete: (PastTripItem) -> Unit = remember(pastTripItems.toList()) {
        { pastTrip ->
            val index = pastTripItems.indexOfFirst { it.id == pastTrip.id }
            if (index != -1) pastTripItems[index] = pastTrip.copy(isPaid = !pastTrip.isPaid)
        }
    }

    val onDeleteTrip: (PastTripItem) -> Unit = remember(pastTripItems.toList()) {
        { expenseItem ->
            pastTripItems.remove(expenseItem)
        }
    }

    val onAddTrip: (String) -> Unit = remember {
        { title ->
            pastTripItems.add(
                PastTripItem(
                    id = nextId++,
                    title = title,
                    categories = listOf("New"),
                    amount = 250,
                    date = "April 2026"
                )
            )
        }
    }

    // ── State flows DOWN to stateless child ──
    PastTripsContent(
        pastTrips = filteredPastTrips,
        searchQuery = searchQuery,
        showCompleted = showCompleted,
        completedCount = completedCount,
        totalCount = totalCount,
        allDone = allDone,
        onSearchQueryChange = { searchQuery = it },
        onShowCompletedToggle = { showCompleted = !showCompleted },
        onToggleComplete = onToggleComplete,
        onDeleteTrip = onDeleteTrip,
        onAddTrip = onAddTrip,
        onClearCompleted = { pastTripItems.removeAll { it.isPaid } },
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
    allDone: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onShowCompletedToggle: () -> Unit,
    onToggleComplete: (PastTripItem) -> Unit,
    onDeleteTrip: (PastTripItem) -> Unit,
    onAddTrip: (String) -> Unit,
    onClearCompleted: () -> Unit,
    onTripClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // ✅ remember — local UI state scoped only to this composable
    var newPastTripText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // ── Header ──
        Text(
            text = "Past Trips Expenses",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (allDone) "✅ All past trips covered!"
            else "$completedCount / $totalCount covered!",
            style = MaterialTheme.typography.bodyMedium,
            color = if (allDone) Color(0xFF43A047)
            else MaterialTheme.colorScheme.onSurfaceVariant
        )

        HorizontalDivider()

        // ── Search bar — state hoisting pattern ──
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange
        )

        // ── Filter toggle ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${pastTrips.size} past trip(s) shown",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = showCompleted,
                    onCheckedChange = { onShowCompletedToggle() }
                )
                Text("Show completed", style = MaterialTheme.typography.bodySmall)
            }
        }

        HorizontalDivider()

        // ── Past Trips list — key = stable ID, not position ──
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pastTrips, key = { it.id }) { pastTrips ->
                PastTripRow(
                    pastTrip = pastTrips,
                    onToggleComplete = onToggleComplete,
                    onDelete = onDeleteTrip,
                    onClick = {onTripClick(pastTrips.id)}
                )
            }

            if (pastTrips.isEmpty()) {
                item {
                    Text(
                        text = "No past Trips found.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        HorizontalDivider()

        // ── Add new past Trip ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newPastTripText,
                onValueChange = { newPastTripText = it },
                label = { Text("New past Trip") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    onAddTrip(newPastTripText)
                    newPastTripText = ""
                },
                enabled = newPastTripText.isNotBlank()
            ) {
                Text("Add")
            }
        }

        // ── Clear completed ──
        OutlinedButton(
            onClick = onClearCompleted,
            enabled = completedCount > 0,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Clear Completed ($completedCount)")
        }
    }
}

// STATELESS TASK ROW
@Composable
fun PastTripRow(
    pastTrip: PastTripItem,
    onToggleComplete: (PastTripItem) -> Unit,
    onDelete: (PastTripItem) -> Unit,
    onClick: (PastTripItem) -> Unit,
    modifier: Modifier = Modifier
) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick(pastTrip) },
        colors = CardDefaults.cardColors(
            containerColor = if (pastTrip.isPaid)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = pastTrip.isPaid,
                    onCheckedChange = { onToggleComplete(pastTrip) }
                )
                Column {
                    Text(
                        text = pastTrip.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (pastTrip.isPaid)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = pastTrip.categories.joinToString(", "),
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = { onDelete(pastTrip) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PastTripsPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            PastTripScreen()
        }
    }
}