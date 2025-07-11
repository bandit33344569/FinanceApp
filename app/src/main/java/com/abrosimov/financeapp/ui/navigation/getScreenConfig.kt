package com.abrosimov.financeapp.ui.navigation

import androidx.navigation3.runtime.NavKey
import com.abrosimov.financeapp.ui.navigation.screensconfigs.ScreenConfig
import com.abrosimov.financeapp.ui.navigation.screens.AccountEdit
import com.abrosimov.financeapp.ui.navigation.screens.MainAppScreen
import com.abrosimov.financeapp.ui.navigation.screens.TransactionEditScreen
import com.abrosimov.financeapp.ui.navigation.screensconfigs.createAccountConfig
import com.abrosimov.financeapp.ui.navigation.screensconfigs.createAccountEditConfig
import com.abrosimov.financeapp.ui.navigation.screensconfigs.createArticlesConfig
import com.abrosimov.financeapp.ui.navigation.screensconfigs.createHistoryExpensesConfig
import com.abrosimov.financeapp.ui.navigation.screensconfigs.createHistoryIncomesConfig
import com.abrosimov.financeapp.ui.navigation.screensconfigs.createSettingConfig
import com.abrosimov.financeapp.ui.navigation.screensconfigs.createTodayExpensesConfig
import com.abrosimov.financeapp.ui.navigation.screensconfigs.createTodayIncomesConfig
import com.abrosimov.financeapp.ui.navigation.screensconfigs.createTransactionEditScreenConfig
import com.abrosimov.ui.navigation.HistoryType
import com.abrosimov.ui.viewmodel.SharedAppViewModel

/**
 * Возвращает конфигурацию экрана на основе текущего ключа навигации.
 *
 * Формирует объект [ScreenConfig], содержащий информацию о:
 * - Заголовке экрана
 * - Иконке навигации (например, кнопка "Назад")
 * - Действиях в тулбаре (например, иконки с функционалом)
 * - Видимости и поведении FAB (FloatingActionButton)
 *
 * Поддерживает основные экраны приложения (`MainAppScreen`) и историю.
 * HystoryType - специальный тип, с помощью которого можно открыть соотвествующий экран(расходов или доходов)
 *
 * @param screen Текущий экран, представленный как [NavKey].
 * @param navigateToHistoryExpense Функция для перехода к истории расходов. Может быть null.
 * @param navigateToHistoryIncome Функция для перехода к истории доходов. Может быть null.
 * @param navigateBack Функция для возврата назад. Может быть null.
 * @return Объект [ScreenConfig], содержащий параметры отображения экрана.
 */
fun getScreenConfig(
    screen: NavKey,
    navigateToHistoryExpense: (() -> Unit)? = null,
    navigateToHistoryIncome: (() -> Unit)? = null,
    onNavigateToAccountEdit: (() -> Unit)? = null,
    navigateBack: (() -> Unit)? = null,
    sharedAppViewModel: SharedAppViewModel
): ScreenConfig =
    when (screen) {
        MainAppScreen.Expenses -> createTodayExpensesConfig(navigateToHistoryExpense)
        MainAppScreen.Income -> createTodayIncomesConfig(navigateToHistoryIncome)
        MainAppScreen.Settings -> createSettingConfig()
        MainAppScreen.Articles -> createArticlesConfig()
        MainAppScreen.Account -> createAccountConfig(onNavigateToAccountEdit)
        HistoryType.Income -> createHistoryIncomesConfig(navigateBack)
        HistoryType.Expenses -> createHistoryExpensesConfig(navigateBack)
        is TransactionEditScreen -> createTransactionEditScreenConfig(
            onCancelClick = navigateBack,
            onOkClick = navigateBack,
            sharedAppViewModel = sharedAppViewModel
        )

        AccountEdit -> createAccountEditConfig(
            onCancelClick = navigateBack,
            onOkClick = navigateBack,
            sharedAppViewModel = sharedAppViewModel
        )

        else -> {
            ScreenConfig(
                title = "неопознанный экран"
            )
        }
    }
