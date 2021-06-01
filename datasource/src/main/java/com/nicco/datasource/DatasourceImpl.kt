package com.nicco.datasource

import com.nicco.api.Api
import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff
import com.nicco.api.utils.Either
import java.lang.Exception

class DatasourceImpl(private val api: Api) : Datasource {
    override suspend fun findListProducts(): Either<String, List<ProductResponse>> =
        api.findListProducts()

    override suspend fun loadBffProducts(): Either<String, List<ProductResponseBff>> =
        api.loadBffProducts()
}