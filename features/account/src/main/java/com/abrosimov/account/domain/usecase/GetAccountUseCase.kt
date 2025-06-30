package com.abrosimov.account.domain.usecase

import com.abrosimov.account.domain.repository.AccountRepository
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.Account
import com.abrosimov.core.domain.retryWithDelay
import javax.inject.Inject

/**
 * UseCase для получения информации о счете с поддержкой повторных попыток.
 *
 * Использует [AccountRepository] для получения данных и применяет логику повтора с задержкой
 * в случае сетевых или временных ошибок.
 *
 * @property repository Репозиторий, предоставляющий данные о счете.
 */
class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): Resource<Account> {
        return retryWithDelay {
            repository.getAccount()
        }
    }
}