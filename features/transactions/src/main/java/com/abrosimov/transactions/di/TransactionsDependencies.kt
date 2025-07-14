package com.abrosimov.transactions.di

import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.repository.AccountDetailsRepository

interface TransactionsDependencies {
    val transactionRepository: TransactionRepository
    val accountDetailsRepository: AccountDetailsRepository
    val categoriesRepository: CategoriesRepository
}