package presentation.ui.sharedComponents

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtonVariations() {

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Filled — highest emphasis
        Button(
            onClick = {
                Toast.makeText(context, "Save clicked", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Save")
        }

        // Outlined — secondary action
        OutlinedButton(
            onClick = {
                Toast.makeText(context, "Cancel clicked", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Cancel")
        }

        // Text — lowest emphasis
        TextButton(
            onClick = {
                Toast.makeText(context, "Learn More clicked", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Learn More")
        }

        // Elevated — stands out from background
        ElevatedButton(
            onClick = {
                Toast.makeText(context, "Upload clicked", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Upload")
        }

        // Icon-only button
        IconButton(
            onClick = {
                Toast.makeText(context, "Favorite clicked", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Like"
            )
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = {
                Toast.makeText(context, "FAB clicked", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add new item"
            )
        }

        // Extended Floating Action Button
        ExtendedFloatingActionButton(
            onClick = {
                Toast.makeText(context, "New Note clicked", Toast.LENGTH_SHORT).show()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            },
            text = {
                Text("New Note")
            }
        )
    }
}

// --- Previews ---

@Preview(name = "All Button Variations", showBackground = true)
@Composable
fun PreviewButtonVariations() {
    MaterialTheme {
        ButtonVariations()
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ButtonVariations()
            }
        }
    }
}