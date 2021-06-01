package com.nicco.business

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nicco.business.mapper.ProductMapper
import com.nicco.business.model.ProductModelUi
import com.nicco.repository.Repository
import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff
import com.nicco.api.utils.Either
import com.nicco.business.usecase.UseCaseProducts
import com.nicco.business.usecase.UseCaseProductsImpl
import com.nicco.business.utils.TestCoroutineRule
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class UseCaseProductsTest {


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    val repository = mockk<Repository>()
    val mapper = mockk<ProductMapper>()

    lateinit var useCaseProducts: UseCaseProducts

    @Test
    fun whenUseCaseProducts_callsFindListProduct_shouldReturnListOfProducts(){
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Right(emptyList())

            coEvery {
                repository.findListProducts()
            } returns response

            val listToUi = arrayListOf<ProductModelUi>()

            every {
                mapper.mapToUi(emptyList())
            } returns listToUi


            useCaseProducts = UseCaseProductsImpl(mapper, repository)
            val result = useCaseProducts.findListProducts()
            assert(result.isRight)
            coVerify { repository.findListProducts() }
            verify { mapper.mapToUi(emptyList()) }
        }
    }

    @Test
    fun whenUseCaseProducts_callsFindListProduct_shouldReturnError(){
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Left("Error")

            coEvery {
                repository.findListProducts()
            } returns response

            useCaseProducts = UseCaseProductsImpl(mapper, repository)
            val result = useCaseProducts.findListProducts()

            assert(result.isLeft)
            coVerify { repository.findListProducts() }
        }
    }

    @Test
    fun whenUseCaseProducts_callsReloadListToExpensiveProducts_shouldReturnListOfProducts(){
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Right(emptyList())

            coEvery {
                repository.reloadListToExpensiveProducts()
            } returns response

            val listToUi = arrayListOf<ProductModelUi>()

            every {
                mapper.mapToUi(emptyList())
            } returns listToUi


            useCaseProducts = UseCaseProductsImpl(mapper, repository)
            val result = useCaseProducts.reloadListToExpensiveProducts()
            assert(result.isRight)
            coVerify { repository.reloadListToExpensiveProducts() }
            verify { mapper.mapToUi(emptyList()) }
        }
    }

    @Test
    fun whenUseCaseProducts_callsReloadListToExpensiveProducts_shouldReturnError(){
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Left("Error")

            coEvery {
                repository.reloadListToExpensiveProducts()
            } returns response

            val listToUi = arrayListOf<ProductModelUi>()

            every {
                mapper.mapToUi(emptyList())
            } returns listToUi


            useCaseProducts = UseCaseProductsImpl(mapper, repository)
            val result = useCaseProducts.reloadListToExpensiveProducts()
            assert(result.isLeft)
            coVerify { repository.reloadListToExpensiveProducts() }
        }
    }

    @Test
    fun whenUseCaseProducts_callsReloadListToCheaperProducts_shouldReturnListOfProducts(){
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Right(emptyList())

            coEvery {
                repository.reloadListToCheaperProducts()
            } returns response

            val listToUi = arrayListOf<ProductModelUi>()

            every {
                mapper.mapToUi(emptyList())
            } returns listToUi


            useCaseProducts = UseCaseProductsImpl(mapper, repository)
            val result = useCaseProducts.reloadListToCheaperProducts()
            assert(result.isRight)
            coVerify { repository.reloadListToCheaperProducts() }
            verify { mapper.mapToUi(emptyList()) }
        }
    }

    @Test
    fun whenUseCaseProducts_callsReloadListToCheaperProducts_shouldReturnListOfError(){
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Left("Error")

            coEvery {
                repository.reloadListToCheaperProducts()
            } returns response

            val listToUi = arrayListOf<ProductModelUi>()

            every {
                mapper.mapToUi(emptyList())
            } returns listToUi


            useCaseProducts = UseCaseProductsImpl(mapper, repository)
            val result = useCaseProducts.reloadListToCheaperProducts()
            assert(result.isLeft)
            coVerify { repository.reloadListToCheaperProducts() }
        }
    }

    @Test
    fun whenUseCaseProducts_callsLoadBffProducts_shouldReturnListOfProducts(){
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponseBff>> = Either.Right(emptyList())

            coEvery {
                repository.loadBffProducts()
            } returns response

            val listToUi = arrayListOf<ProductModelUi>()

            every {
                mapper.mapToBffUi(emptyList())
            } returns listToUi


            useCaseProducts = UseCaseProductsImpl(mapper, repository)
            val result = useCaseProducts.loadBffProducts()
            assert(result.isRight)
            coVerify { repository.loadBffProducts() }
            verify { mapper.mapToBffUi(emptyList()) }
        }
    }

    @Test
    fun whenUseCaseProducts_callsLoadBffProducts_shouldReturnError(){
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponseBff>> = Either.Left("Error")

            coEvery {
                repository.loadBffProducts()
            } returns response

            val listToUi = arrayListOf<ProductModelUi>()

            every {
                mapper.mapToBffUi(emptyList())
            } returns listToUi


            useCaseProducts = UseCaseProductsImpl(mapper, repository)
            val result = useCaseProducts.loadBffProducts()

            assert(result.isLeft)
            coVerify { repository.loadBffProducts() }
        }
    }

}