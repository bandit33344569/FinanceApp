package com.abrosimov.financeapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abrosimov.financeapp.domain.models.Category
import com.abrosimov.financeapp.ui.misc.CategoryListItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen() {
    val categories = listOf(
        Category(1, "Аренда квартиры", "🏠"),
        Category(2, "Одежда", "👗"),
        Category(3, "На собачку", "🐶"),
        Category(4, "Ремонт квартиры", "🛠️"),
        Category(5, "Продукты", "🛒"),
        Category(6, "Спортзал", "🏃‍♂️"),
        Category(7, "Медицина", "💊")
    )

    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            modifier = Modifier,
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            onSearch = {},
            placeholder = { Text("Найти статью") },
            trailingIcon = { Icon(Icons.Default.Search, contentDescription = "поиск") }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            state = searchBarState,
            inputField = inputField,
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        )
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(categories.size) { index ->
                CategoryListItem(categories[index])
            }
        }
    }
}


