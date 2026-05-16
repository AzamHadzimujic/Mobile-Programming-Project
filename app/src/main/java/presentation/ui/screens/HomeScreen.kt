package presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import model.ScreenShortcutData
import presentation.ui.R
import presentation.ui.components.ErrorScreen
import presentation.ui.components.InfoBox
import presentation.ui.components.InputField
import presentation.ui.components.LoadingScreen
import presentation.ui.components.SwitchNotification
import presentation.viewmodel.home.HomeShortcutUiState
import presentation.viewmodel.home.HomeShortcutViewModel

@Composable
fun Homescreen(
    viewModel: HomeShortcutViewModel,
    onScreenClick: (String) -> Unit
) {
    val uiState by viewModel
        .uiState
        .collectAsStateWithLifecycle()
    when (uiState) {
        HomeShortcutUiState.Loading -> {
            LoadingScreen()
        }
        is HomeShortcutUiState.Error -> {
            ErrorScreen(
                message = (uiState as HomeShortcutUiState.Error).message
            )
        }
        is HomeShortcutUiState.Success -> {
            val state = uiState as HomeShortcutUiState.Success
            HomescreenContent(
                searchQuery = state.searchQuery,
                shortcuts = state.shortcuts,
                onSearchQueryChange = {},
                onScreenClick = onScreenClick
            )
        }
        HomeShortcutUiState.Init -> {
            // do nothing
        }
    }
}
@Composable
fun HomescreenContent(
    searchQuery: String,
    shortcuts: List<ScreenShortcutData>,
    onSearchQueryChange: (String) -> Unit,
    onScreenClick: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.vacation1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome to Trip Budget Planner",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            InputField(
                "Search shortcuts",
                searchQuery,
                onSearchQueryChange
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                item {
                    LazyRow {
                        shortcuts.take(2).forEach { item ->
                            item {
                                InfoBox(
                                    text = item.title,
                                    backgroundColor = Color.Green,
                                    onClick = {
                                        onScreenClick(item.route)
                                    }
                                )
                            }
                        }
                    }
                }
                item {
                    LazyRow {
                        shortcuts.drop(2).take(2).forEach { item ->
                            item {
                                InfoBox(
                                    text = item.title,
                                    backgroundColor = Color.Magenta,
                                    onClick = {
                                        onScreenClick(item.route)
                                    }
                                )
                            }
                        }
                    }
                }
                item {
                    SwitchNotification()
                }
            }
        }
    }
}