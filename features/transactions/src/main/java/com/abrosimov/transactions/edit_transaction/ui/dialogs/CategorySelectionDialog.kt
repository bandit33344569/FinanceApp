package com.abrosimov.transactions.edit_transaction.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abrosimov.impl.models.Category
import com.abrosimov.transactions.edit_transaction.ui.viewmodel.TransactionEditViewModel
import com.abrosimov.ui.composableFunctions.CustomListItem
import com.abrosimov.utils.models.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectionDialog(
    onCategorySelected: (Category) -> Unit,
    onDismiss: () -> Unit,
    viewModel: TransactionEditViewModel
) {
    val incomeCategories = viewModel.incomesCategories.collectAsState()
    val expenseCategories = viewModel.expensesCategories.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                "Доходы", style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally))

                when (val incomeRes = incomeCategories.value) {
                    is Resource.Success -> {
                        LazyColumn {
                            items(incomeRes.data) { category ->
                                CategoryItem(
                                    category = category,
                                    onClick = { onCategorySelected(category) }
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        Text("Ошибка загрузки доходов: ${incomeRes.message}")
                    }

                    Resource.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }

                        Spacer (modifier = Modifier.height(16.dp))

                        Text (
                        "Расходы",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            when (val expenseRes = expenseCategories.value) {
                is Resource.Success -> {
                    LazyColumn {
                        items(expenseRes.data) { category ->
                            CategoryItem(
                                category = category,
                                onClick = { onCategorySelected(category) }
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    Text("Ошибка загрузки расходов: ${expenseRes.message}")
                }

                Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }

            CustomListItem(
                leftTitle = "Отмена",
                leftIcon = "⊗",
                listHeight = 70,
                listBackground = MaterialTheme.colorScheme.error,
                leftIconBackground = MaterialTheme.colorScheme.error,
                clickable = true,
                onClick = onDismiss
            )
        }
    }
}


@Composable
fun CategoryItem(
    category: Category,
    onClick: () -> Unit
) {
    CustomListItem(
        leftTitle = category.name,
        leftIcon = category.emoji,
        listBackground = MaterialTheme.colorScheme.background,
        leftIconBackground = MaterialTheme.colorScheme.secondary,
        clickable = true,
        onClick = onClick
    )
}