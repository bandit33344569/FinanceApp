package com.abrosimov.financeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abrosimov.financeapp.domain.models.Category
import com.abrosimov.financeapp.ui.misc.CustomListItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen() {
    val categories = listOf(
        Category(1, "Аренда квартиры", "🏠", false),
        Category(2, "Одежда", "👗", false),
        Category(3, "На собачку", "🐶", false),
        Category(4, "Ремонт квартиры", "🛠️", false),
        Category(5, "Продукты", "🛒", false),
        Category(6, "Спортзал", "🏃‍♂️", false),
        Category(7, "Медицина", "💊", false)
    )

    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()

    val inputField = @Composable{
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
            modifier = Modifier.height(56.dp).fillMaxWidth()
        )
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(categories.size) { index ->
                categories[index].let { category ->
                    CustomListItem(
                        modifier = Modifier.height(70.dp),
                        leadingContent = {
                            Text(
                                text = category.emoji,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.tertiary
                                ),
                                modifier = Modifier.background(MaterialTheme.colorScheme.secondary,CircleShape)
                            )
                        },
                        headlineContent = { Text(category.name) },
                    )
                }
            }
        }
    }
}


