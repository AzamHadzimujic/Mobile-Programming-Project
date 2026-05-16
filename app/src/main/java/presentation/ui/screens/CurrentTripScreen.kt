package presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import presentation.viewmodel.currentTrip.CurrentTripUiState
import presentation.viewmodel.currentTrip.CurrentTripViewModel
import presentation.ui.components.SearchBar

@Composable
fun CurrentTripScreen(
    tripId: String,
    viewModel: CurrentTripViewModel = hiltViewModel(),
    onAddExpenseClick: () -> Unit
) {
    LaunchedEffect(tripId) {
        viewModel.loadInitialExpenses(tripId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        is CurrentTripUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
        is CurrentTripUiState.Error -> {
            val error = uiState as CurrentTripUiState.Error
            Text(
                text = error.message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        is CurrentTripUiState.Success -> {
            val state = uiState as CurrentTripUiState.Success
            CurrentTripContent(
                expenses = state.expenses,
                searchQuery = state.searchQuery,
                onSearchQueryChange = viewModel::updateSearch,
                onAddExpense = {
                    onAddExpenseClick()
                },
                onDeleteExpense = viewModel::deleteExpense
            )
        }
        CurrentTripUiState.Init -> {
            // do nothing
        }
    }
}

// ─────────────────────────────────────────────────────────────
// STATELESS CONTENT — owns no state
// ────────────────────────────────────────────────────────────-

@Composable
fun CurrentTripContent(
    expenses: List<model.ExpenseItem>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddExpense: (String) -> Unit,
    onDeleteExpense: (String) -> Unit
) {
    var newExpenseText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Current Trip Expenses",
            style = MaterialTheme.typography.headlineSmall
        )
        // SEARCH
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange
        )
        // LIST
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(expenses, key = { it.id }) { expense ->
                ExpenseRow(
                    expense = expense,
                    onDelete = { onDeleteExpense(expense.id) }
                )
            }
            if (expenses.isEmpty()) {
                item {
                    Text(
                        text = "No expenses found",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        HorizontalDivider()
        // ADD EXPENSE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = newExpenseText,
                onValueChange = { newExpenseText = it },
                modifier = Modifier.weight(1f),
                label = { Text("New Expense") }
            )
            Button(
                onClick = {
                    onAddExpense(newExpenseText)
                    newExpenseText = ""
                },
                enabled = newExpenseText.isNotBlank()
            ) {
                Text("Add")
            }
        }
    }
}
// STATELESS TASK ROW
@Composable
fun ExpenseRow(
    expense: model.ExpenseItem,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(expense.title)
                Text(expense.categories)
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}