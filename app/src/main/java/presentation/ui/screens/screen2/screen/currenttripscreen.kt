package presentation.ui.screens.screen2.screen

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
import androidx.compose.runtime.Stable
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
import model.ExpenseItem

// STATEFUL SCREEN — single source of truth for all state
@Composable
fun CurrentTripScreen() {

    // ✅ mutableStateListOf — observable list
    val expenseItems = remember {
        mutableStateListOf(
            ExpenseItem(1, "DoorDash delivery", false, listOf("Food", "Delivery"), 25),
            ExpenseItem(2, "Museum Visit", true, listOf("Culture", "Visit"), 30),
            ExpenseItem(3, "Lunch at the beach", true, listOf("Food", "Restaurant"), 150),
            ExpenseItem(4, "Public Transport", true, listOf("Personal", "Transport"), 10),
        )
    }

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showCompleted by rememberSaveable { mutableStateOf(true) }

    // ✅ derivedStateOf — only recomposes when result changes
    val filteredExpenses by remember {
        derivedStateOf {
            expenseItems.filter { expense ->
                val matchesQuery = expense.title.contains(searchQuery, ignoreCase = true)
                val matchesFilter = if (showCompleted) true else !expense.isPaid
                matchesQuery && matchesFilter
            }
        }
    }

    val completedCount by remember { derivedStateOf { expenseItems.count { it.isPaid } } }
    val totalCount by remember { derivedStateOf { expenseItems.size } }
    val allDone by remember { derivedStateOf { expenseItems.isNotEmpty() && completedCount == totalCount } }

    var nextId by remember { mutableStateOf(expenseItems.size + 1) }

    // ✅ remember(key) — stable lambda, not recreated on every recomposition
    val onToggleComplete: (ExpenseItem) -> Unit = remember(expenseItems.toList()) {
        { expense ->
            val index = expenseItems.indexOfFirst { it.id == expense.id }
            if (index != -1) expenseItems[index] = expense.copy(isPaid = !expense.isPaid)
        }
    }

    val onDeleteExpense: (ExpenseItem) -> Unit = remember(expenseItems.toList()) {
        { expenseItem ->
            expenseItems.remove(expenseItem)
        }
    }

    val onAddExpense: (String) -> Unit = remember {
        { title ->
            expenseItems.add(ExpenseItem(id = nextId++, title = title, categories = listOf("New"), amount = 50))
        }
    }

    // ── State flows DOWN to stateless child ──
    ExpensesContent(
        expenses = filteredExpenses,
        searchQuery = searchQuery,
        showCompleted = showCompleted,
        completedCount = completedCount,
        totalCount = totalCount,
        allDone = allDone,
        onSearchQueryChange = { searchQuery = it },
        onShowCompletedToggle = { showCompleted = !showCompleted },
        onToggleComplete = onToggleComplete,
        onDeleteExpense = onDeleteExpense,
        onAddExpense = onAddExpense,
        onClearCompleted = { expenseItems.removeAll { it.isPaid } }
    )
}

// ─────────────────────────────────────────────────────────────
// STATELESS CONTENT — owns no state
// ────────────────────────────────────────────────────────────-

@Composable
fun ExpensesContent(
    expenses: List<ExpenseItem>,
    searchQuery: String,
    showCompleted: Boolean,
    completedCount: Int,
    totalCount: Int,
    allDone: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onShowCompletedToggle: () -> Unit,
    onToggleComplete: (ExpenseItem) -> Unit,
    onDeleteExpense: (ExpenseItem) -> Unit,
    onAddExpense: (String) -> Unit,
    onClearCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    // ✅ remember — local UI state scoped only to this composable
    var newExpenseText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // ── Header ──
        Text(
            text = "Current Trip Expenses",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (allDone) "✅ All expenses covered!"
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
                text = "${expenses.size} expense(s) shown",
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

        // ── Expense list — key = stable ID, not position ──
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(expenses, key = { it.id }) { expense ->
                ExpenseRow(
                    expense = expense,
                    onToggleComplete = onToggleComplete,
                    onDelete = onDeleteExpense
                )
            }

            if (expenses.isEmpty()) {
                item {
                    Text(
                        text = "No expenses found.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        HorizontalDivider()

        // ── Add new Expense ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newExpenseText,
                onValueChange = { newExpenseText = it },
                label = { Text("New Expense") },
                singleLine = true,
                modifier = Modifier.weight(1f)
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

// ─────────────────────────────────────────────────────────────
// STATELESS SEARCH BAR
// ─────────────────────────────────────────────────────────────

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Search expenses") },
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

// STATELESS TASK ROW
@Composable
fun ExpenseRow(
    expense: ExpenseItem,
    onToggleComplete: (ExpenseItem) -> Unit,
    onDelete: (ExpenseItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (expense.isPaid)
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
                    checked = expense.isPaid,
                    onCheckedChange = { onToggleComplete(expense) }
                )
                Column {
                    Text(
                        text = expense.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (expense.isPaid)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = expense.categories.joinToString(", "),
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = { onDelete(expense) }) {
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
fun CurrentTripPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CurrentTripScreen()
        }
    }
}