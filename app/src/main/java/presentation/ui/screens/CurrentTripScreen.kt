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
import model.data.local.entity.ExpenseEntity
import presentation.ui.components.SearchBar
import presentation.viewmodel.currentTrip.CurrentTripUiState
import presentation.viewmodel.currentTrip.CurrentTripViewModel

@Composable
fun CurrentTripScreen(
    viewModel: CurrentTripViewModel = hiltViewModel(),
    onAddExpenseClick: () -> Unit
) {
    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()
    when (uiState) {
        is CurrentTripUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        is CurrentTripUiState.Error -> {
            val error =
                uiState as CurrentTripUiState.Error
            Text(
                text = error.message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        is CurrentTripUiState.Success -> {
            val state =
                uiState as CurrentTripUiState.Success
            CurrentTripContent(
                expenses = state.expenses,
                searchQuery = state.searchQuery,
                onSearchQueryChange =
                    viewModel::updateSearch,
                onAddExpense = {
                    onAddExpenseClick()
                },
                onDeleteExpense =
                    viewModel::deleteExpense
            )
        }
        CurrentTripUiState.Init -> {
            // do nothing
        }
    }
}
@Composable
fun CurrentTripContent(
    expenses: List<ExpenseEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddExpense: () -> Unit,
    onDeleteExpense: (ExpenseEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Current Trip Expenses",
            style =
                MaterialTheme.typography.headlineSmall
        )
        // SEARCH
        SearchBar(
            query = searchQuery,
            onQueryChange =
                onSearchQueryChange
        )
        // LIST
        LazyColumn(
            modifier = Modifier.weight(1f),

            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {
            items(
                expenses,
                key = { it.id }
            ) { expense ->
                ExpenseRow(
                    expense = expense,
                    onDelete = {
                        onDeleteExpense(expense)
                    }
                )
            }
            if (expenses.isEmpty()) {
                item {
                    Text(
                        text = "No expenses found",
                        style =
                            MaterialTheme.typography.bodyMedium,
                        modifier =
                            Modifier.padding(16.dp)
                    )
                }
            }
        }
        HorizontalDivider()
        // ADD BUTTON
        Button(
            onClick = {
                onAddExpense()
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Expense")
        }
    }
}
@Composable
fun ExpenseRow(
    expense: ExpenseEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {
            Column {
                Text(expense.title)
                Text(expense.categories)
                Text("$${expense.amount}")
            }
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    imageVector =
                        Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}