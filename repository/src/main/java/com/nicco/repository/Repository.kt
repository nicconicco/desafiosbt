package com.nicco.repository

import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff
import com.nicco.api.utils.Either


interface RepositoryCache {
    fun hasCache(): Boolean
    fun getCache(): List<ProductResponse>?
    fun saveCache(list: List<ProductResponse>)
}

interface Repository {
    suspend fun findListProducts(forceUpdate: Boolean = false): Either<String, List<ProductResponse>>
    suspend fun reloadListToExpensiveProducts(): Either<String, List<ProductResponse>>
    suspend fun reloadListToCheaperProducts(): Either<String, List<ProductResponse>>
    suspend fun loadBffProducts(): Either<String, List<ProductResponseBff>>
    suspend fun showProductsWithThisValue(value: Float): Either<String, List<ProductResponse>>
}