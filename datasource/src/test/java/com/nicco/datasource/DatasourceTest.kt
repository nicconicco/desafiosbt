package com.nicco.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nicco.api.Api
import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff
import com.nicco.api.utils.Either
import com.nicco.datasource.utils.TestCoroutineRule
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.Exception

class DatasourceTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val api = mockk<Api>()
    lateinit var datasource: Datasource

    @Test
    fun whenDatasource_callsFindListProduct_shouldReturnListOfProducts() {
        testCoroutineRule.runBlockingTest {
            val products: List<ProductResponse> = arrayListOf()
            val list: Either<String, List<ProductResponse>> = Either.Right(products)

            coEvery {
                api.findListProducts()
            } returns list

            datasource = DatasourceImpl(api)
            val result = datasource.findListProducts()

            assert(result.isRight)
            coVerify { api.findListProducts() }
        }
    }

    @Test
    fun whenDatasource_callsFindListProduct_shoulThrowException() {
        testCoroutineRule.runBlockingTest {
            val leftResponse: Either<String, List<ProductResponse>> = Either.Left("Erro")

            coEvery {
                api.findListProducts()
            } returns leftResponse

            datasource = DatasourceImpl(api)
            val result = datasource.findListProducts()

            assert(result.isLeft)
            coVerify { api.findListProducts() }
        }
    }

    @Test
    fun whenDatasource_callsLoadBffProducts_shouldReturnListOfProducts() {
        testCoroutineRule.runBlockingTest {
            val products: List<ProductResponseBff> = arrayListOf()
            val list: Either<String, List<ProductResponseBff>> = Either.Right(products)

            coEvery {
                api.loadBffProducts()
            } returns list

            datasource = DatasourceImpl(api)
            val result = datasource.loadBffProducts()

            assert(result.isRight)
            coVerify { api.loadBffProducts() }
        }
    }

    @Test
    fun whenDatasource_callsLoadBffProducts_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val list: Either<String, List<ProductResponseBff>> = Either.Left("Error")

            coEvery {
                api.loadBffProducts()
            } returns list

            datasource = DatasourceImpl(api)
            val result = datasource.loadBffProducts()

            assert(result.isLeft)
            coVerify { api.loadBffProducts() }
        }
    }
}