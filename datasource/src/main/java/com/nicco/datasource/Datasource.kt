package com.nicco.datasource

import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff
import com.nicco.api.utils.Either

interface Datasource {
    suspend fun findListProducts() : Either<String, List<ProductResponse>>
    suspend fun loadBffProducts(): Either<String, List<ProductResponseBff>>
}