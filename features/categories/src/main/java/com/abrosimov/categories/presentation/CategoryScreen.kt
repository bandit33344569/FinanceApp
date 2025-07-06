package com.abrosimov.categories.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.core.di.LocalViewModelFactory
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.Category


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(viewModel: CategoriesViewModel = viewModel(factory = LocalViewModelFactory.current)) {
    LaunchedEffect(Unit) { viewModel.loadCategories() }
    val state = viewModel.filteredCategoriesWithResource.collectAsState()
    var searchQuery = viewModel.searchQuery.collectAsState()






    Column(modifier = Modifier.fillMaxSize()) {
        SearchField(
            query = searchQuery.value,
            onQueryChange = { viewModel.updateSearchQuery(it) }
        )
        DismissKeyboardOnTapOutside {
            HorizontalDivider()
            when (state.value) {
                is Resource.Loading -> CircularProgressIndicator()
                is Resource.Success -> {
                    val categories = (state.value as Resource.Success<List<Category>>).data
                    LazyColumn {
                        items(categories.size) { index ->
                            CategoryListItem(categories[index])
                            HorizontalDivider()
                        }
                    }
                }

                is Resource.Error -> {
                    Column {
                        Text("Ошибка: ${(state.value as Resource.Error).message}")
                        Button(onClick = viewModel::loadCategories) {
                            Text("Повторить")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth(),
        placeholder = { Text("Поиск") },
        trailingIcon = { Icon(Icons.Default.Search, contentDescription = "поиск") },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun DismissKeyboardOnTapOutside(
    content: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    focusManager.clearFocus()
                }
            )
    ) {
        content()
    }
}

