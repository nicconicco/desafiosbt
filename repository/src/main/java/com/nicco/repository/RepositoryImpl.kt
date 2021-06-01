package com.nicco.repository

import android.util.Log
import com.nicco.datasource.Datasource
import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff
import com.nicco.api.utils.Either

class RepositoryImpl(
    private val datasource: Datasource,
    private val repositoryCache: RepositoryCache
) : Repository {

    override suspend fun findListProducts(forceUpdate: Boolean): Either<String, List<ProductResponse>> {
        if (!forceUpdate) {
            if (repositoryCache.hasCache()) {
                return Either.Right(repositoryCache.getCache()!!)
            }
        }

        val response = datasource.findListProducts()

        when (response) {
            is Either.Right -> {
                repositoryCache.saveCache(response.b)
            }
            else -> {
                Log.d("noCache", "somenthing wrong")
            }
        }

        return response
    }


    override suspend fun reloadListToExpensiveProducts(): Either<String, List<ProductResponse>> {
        if (!repositoryCache.hasCache())
            return Either.Left("Lista nao carregada")

        val cache = repositoryCache.getCache()
        val expensiveList = cache!!.sortedByDescending { it.price.toFloat() }
        return Either.Right(expensiveList)
    }

    override suspend fun reloadListToCheaperProducts(): Either<String, List<ProductResponse>> {
        if (!repositoryCache.hasCache())
            return Either.Left("Lista nao carregada")

        val cache = repositoryCache.getCache()
        val expensiveList = cache!!.sortedBy { it.price.toFloat() }
        return Either.Right(expensiveList)
    }

    override suspend fun loadBffProducts(): Either<String, List<ProductResponseBff>> =
        datasource.loadBffProducts()

    override suspend fun showProductsWithThisValue(value: Float): Either<String, List<ProductResponse>> {
        if (!repositoryCache.hasCache())
            return Either.Left("Lista nao carregada")

        val cache = repositoryCache.getCache()
        val expensiveList = cache!!.sortedBy { it.price.toFloat() == value }
        return Either.Right(expensiveList)
    }
}