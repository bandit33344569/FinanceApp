package com.abrosimov.core.domain.models

/**
 *
 *DTO, представляющий транзакцию с дополнительными данными.
 *
 *Используется для передачи информации о транзакции между слоями приложения или через API.
 *Включает в себя не только данные самой транзакции, но и связанную информацию,
 *такую как категория и валюта.
 */
data class SpecTransaction(
    val id: Int,
    val accountId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
    val category: Category,
    val currency: String,
)