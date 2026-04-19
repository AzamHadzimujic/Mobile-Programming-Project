package presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import presentation.ui.components.ButtonSave
import presentation.ui.components.InputField

@Composable
fun AddExpenseScreen(
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val isFormValid = remember {
        androidx.compose.runtime.derivedStateOf {
            title.isNotBlank() &&
                    amount.isNotBlank() &&
                    category.isNotBlank() &&
                    date.isNotBlank()
        }
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ){
        Text(
            "Here you can add a new expense for your current trip!",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            item {
                InputField("Title", title) { title = it }
                InputField("Amount", amount) { amount = it }
                InputField("Category", category) { category = it }
                InputField("Date", date) { date = it }
            }
            item {
                ButtonSave({
                    if (!isFormValid.value) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Expense saved", Toast.LENGTH_SHORT).show()
                        onNavigateBack()
                    }
                },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}