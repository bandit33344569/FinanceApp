package com.abrosimov.api.models.dto

/**
 *
 *DTO, представляющий транзакцию с дополнительными данными.
 *
 *Используется для передачи информации о транзакции между слоями приложения или через API.
 *Включает в себя не только данные самой транзакции, но и связанную информацию,
 *такую как категория и валюта.
 */
data class SpecTransactionDto(
    val id: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
    val category: CategoryDto,
) {
}
