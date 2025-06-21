package com.abrosimov.financeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.abrosimov.financeapp.domain.models.Category
import com.abrosimov.financeapp.domain.repo.Resource
import com.abrosimov.financeapp.ui.FinanceViewModel
import com.abrosimov.financeapp.ui.lists.CategoryListItem


/*
Поиск вроде работает но не открывается клавиатура и русские символы нельзя ввести, в планах фиксы
на эмуляторе Английские символы можно ввести в поле поиска но русские почему-то нельзя, пока не разобрался
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(viewModel: FinanceViewModel) {
    LaunchedEffect(Unit) { viewModel.loadCategories() }
    val state = viewModel.filteredCategoriesWithResource.collectAsState()
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val focusRequester = remember { FocusRequester() }
    val inputField: @Composable () -> Unit = {
        SearchBarDefaults.InputField(
            modifier = Modifier.focusRequester(focusRequester),
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            enabled = true,
            onSearch = {},
            placeholder = { Text("Найти статью") },
            trailingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "поиск",
                    modifier = Modifier.clickable(enabled = true, onClick = {
                        viewModel.updateSearchQuery(textFieldState.text.toString())
                    })
                )
            }
        )
    }


    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            state = searchBarState,
            inputField = inputField,
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
        )
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


