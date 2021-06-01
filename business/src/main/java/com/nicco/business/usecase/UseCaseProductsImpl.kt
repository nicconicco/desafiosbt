package com.nicco.business.usecase

import com.nicco.api.utils.Either
import com.nicco.business.mapper.ProductMapper
import com.nicco.business.model.ProductModelUi
import com.nicco.repository.Repository

class UseCaseProductsImpl(
    private val mapper: ProductMapper,
    private val repository: Repository
) : UseCaseProducts {
    override suspend fun findListProducts(forceUpdate: Boolean): Either<String, List<ProductModelUi>> {
        return when (val response = repository.findListProducts(forceUpdate)) {
            is Either.Right -> {
                Either.Right(mapper.mapToUi(response.b))
            }
            is Either.Left -> {
                Either.Left(response.a)
            }
        }
    }

    override suspend fun reloadListToExpensiveProducts(): Either<String, List<ProductModelUi>> {
        return when (val response = repository.reloadListToExpensiveProducts()) {
            is Either.Right -> {
                Either.Right(mapper.mapToUi(response.b))
            }
            is Either.Left -> {
                Either.Left(response.a)
            }
        }
    }

    override suspend fun reloadListToCheaperProducts(): Either<String, List<ProductModelUi>> {
        return when (val response = repository.reloadListToCheaperProducts()) {
            is Either.Right -> {
                Either.Right(mapper.mapToUi(response.b))
            }
            is Either.Left -> {
                Either.Left(response.a)
            }
        }
    }

    override suspend fun loadBffProducts(): Either<String, List<ProductModelUi>> {
        return when (val response = repository.loadBffProducts()) {
            is Either.Right -> {
                //trick
                Either.Right(mapper.mapToBffUi(response.b))
            }
            is Either.Left -> {
                Either.Left(response.a)
            }
        }
    }
}