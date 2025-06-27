package com.abrosimov.financeapp.di

import androidx.lifecycle.ViewModel
import com.abrosimov.account.presentation.AccountViewModel
import com.abrosimov.categories.presentation.CategoriesViewModel
import com.abrosimov.expenses.presentation.viewmodel.ExpensesViewModel
import com.abrosimov.history.presentation.viewModel.HistoryViewModel
import com.abrosimov.incomes.presentation.IncomesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Модуль Dagger, отвечающий за связывание конкретных ViewModel с их классами через механизм multibinding.
 *
 * Использует `@Binds` и `@IntoMap` вместе с аннотацией-ключом `@ViewModelKey`, чтобы зарегистрировать
 * все необходимые ViewModel в Dagger. Это позволяет фабрике `ViewModelFactory` создавать нужные
 * экземпляры ViewModel на основе запроса.
 */
@Module
interface ViewModelModule {
    /**
     * Регистрирует [ExpensesViewModel] в Dagger как зависимость, связанную с её ключом.
     *
     * @param viewModel Экземпляр [ExpensesViewModel], который будет использоваться при запросе.
     * @return Объект типа [ViewModel], который Dagger может использовать для создания экземпляра.
     */
    @Binds
    @IntoMap
    @ViewModelKey(ExpensesViewModel::class)
    fun bindExpenseViewModel(viewModel: ExpensesViewModel): ViewModel

    /**
     * Регистрирует [CategoriesViewModel] в Dagger как зависимость, связанную с её ключом.
     *
     * @param viewModel Экземпляр [CategoriesViewModel], который будет использоваться при запросе.
     * @return Объект типа [ViewModel], который Dagger может использовать для создания экземпляра.
     */
    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel

    /**
     * Регистрирует [AccountViewModel] в Dagger как зависимость, связанную с её ключом.
     *
     * @param viewModel Экземпляр [AccountViewModel], который будет использоваться при запросе.
     * @return Объект типа [ViewModel], который Dagger может использовать для создания экземпляра.
     */
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel

    /**
     * Регистрирует [IncomesViewModel] в Dagger как зависимость, связанную с её ключом.
     *
     * @param viewModel Экземпляр [IncomesViewModel], который будет использоваться при запросе.
     * @return Объект типа [ViewModel], который Dagger может использовать для создания экземпляра.
     */
    @Binds
    @IntoMap
    @ViewModelKey(IncomesViewModel::class)
    fun bindIncomesViewModel(viewModel: IncomesViewModel): ViewModel

    /**
     * Регистрирует [HistoryViewModel] в Dagger как зависимость, связанную с её ключом.
     *
     * @param viewModel Экземпляр [HistoryViewModel], который будет использоваться при запросе.
     * @return Объект типа [ViewModel], который Dagger может использовать для создания экземпляра.
     */
    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    fun bindHistoryViewModel(viewModel: HistoryViewModel): ViewModel
}