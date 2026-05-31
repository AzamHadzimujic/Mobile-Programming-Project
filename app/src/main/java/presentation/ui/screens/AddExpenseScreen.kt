package presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
import presentation.viewmodel.addExpense.*

@Composable
fun AddExpenseScreen(
    viewModel: AddExpenseViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()
    val formState by viewModel.formState
        .collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect {
            when (it) {
                AddExpenseNavigationEvent.NavigateBack -> {
                    Toast.makeText(
                        context,
                        "Expense saved",
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateBack()
                }
            }
        }
    }
    when (uiState) {
        AddExpenseUiState.Loading -> {
            LoadingScreen()
        }
        is AddExpenseUiState.Error -> {
            ErrorScreen(
                message =
                    (uiState as AddExpenseUiState.Error)
                        .message,
                onRetryClick = {
                    viewModel.resetUiState()
                }
            )
        }
        else -> {
            AddExpenseScreenContent(
                title = formState.title,
                amount = formState.amount,
                category = formState.category,
                onTitleChange =
                    viewModel::onTitleChange,
                onAmountChange =
                    viewModel::onAmountChange,
                onCategoryChange =
                    viewModel::onCategoryChange,
                onSaveClick =
                    viewModel::saveExpense
            )
        }
    }
}
@Composable
fun AddExpenseScreenContent(
    title: String,
    amount: String,
    category: String,
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