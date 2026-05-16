package presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import presentation.ui.components.ButtonSave
import presentation.ui.components.ErrorScreen
import presentation.ui.components.InputField
import presentation.ui.components.LoadingScreen
import presentation.viewmodel.addExpense.AddExpenseUiState
import presentation.viewmodel.addExpense.AddExpenseViewModel

@Composable
fun AddExpenseScreen(
    viewModel: AddExpenseViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel
        .uiState
        .collectAsStateWithLifecycle()

    when (uiState) {
        AddExpenseUiState.Loading -> {
            LoadingScreen()
        }
        is AddExpenseUiState.Error -> {
            ErrorScreen(
                message = (uiState as AddExpenseUiState.Error)
                    .message
            )
        }
        is AddExpenseUiState.Success -> {
            val expenseData =
                (uiState as AddExpenseUiState.Success)
                    .expenseItem
            val isFormValid =
                expenseData.id.isNotBlank() &&
                        expenseData.title.isNotBlank() &&
                        expenseData.amount.isNotBlank() &&
                        expenseData.categories.isNotBlank()
            AddExpenseScreenContent(
                id = expenseData.id,
                title = expenseData.title,
                amount = expenseData.amount,
                category = expenseData.categories,
                onIdChange = {},
                onTitleChange = {},
                onAmountChange = {},
                onCategoryChange = {},
                onSaveClick = {
                    if (!isFormValid) {
                    Toast.makeText(
                        context,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    } else {
                    Toast.makeText(
                        context,
                        "Expense saved",
                        Toast.LENGTH_SHORT
                    ).show()
                        onNavigateBack()
                    }
                }
            )
        }
        AddExpenseUiState.Init -> {
            // do nothing
        }
    }
}
@Composable
fun AddExpenseScreenContent(
    id: String,
    title: String,
    amount: String,
    category: String,
    onIdChange: (String) -> Unit,
    onTitleChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Here you can add a new expense for your current trip!",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                InputField(
                    "Id",
                    id,
                    onChange = onIdChange
                )
                InputField(
                    "Title",
                    title,
                    onTitleChange
                )
                InputField(
                    "Amount",
                    amount,
                    onAmountChange
                )
                InputField(
                    "Category",
                    category,
                    onCategoryChange
                )
            }
            item {
                ButtonSave(
                    onClick = onSaveClick,
                    modifier = Modifier
                )
            }
        }
    }
}