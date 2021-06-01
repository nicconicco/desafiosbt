package com.nicco.business.usecase

import com.nicco.api.utils.Either
import com.nicco.business.model.ProductModelUi

interface UseCaseProducts {
    suspend fun findListProducts(forceUpdate: Boolean = false): Either<String, List<ProductModelUi>>
    suspend fun reloadListToExpensiveProducts(): Either<String, List<ProductModelUi>>
    suspend fun reloadListToCheaperProducts(): Either<String, List<ProductModelUi>>
    suspend fun loadBffProducts(): Either<String, List<ProductModelUi>>
}