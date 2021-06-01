package com.nicco.selecaoglobo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nicco.api.utils.Either
import com.nicco.business.model.ProductModelUi
import com.nicco.business.usecase.UseCaseProducts
import com.nicco.selecaoglobo.utils.Resource
import com.nicco.selecaoglobo.utils.TestCoroutineRule
import com.nicco.selecaoglobo.viewmodel.GloboProductsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import io.mockk.*
import kotlinx.coroutines.Dispatchers

@ExperimentalCoroutinesApi
class GloboProductsViewModelTest {

    val scope = Dispatchers.Unconfined

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    val useCase: UseCaseProducts = mockk()

    val apiProducts: Observer<Resource<List<ProductModelUi>>> = mockk(relaxed = true)

    @Test
    fun givenResponseOk_whenCallLoadProducts_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductModelUi>> = Either.Right(emptyList())

            coEvery {
                useCase.findListProducts()
            } returns response

            val viewModel = GloboProductsViewModel(scope, useCase)
            viewModel.products.observeForever(apiProducts)

            viewModel.loadProducts()

            coVerify { useCase.findListProducts() }

            coVerify {
                apiProducts.onChanged(
                        Resource.loading(
                                null
                        )
                )
            }

            coVerify {
                apiProducts.onChanged(
                        Resource.success(emptyList())
                )
            }

            viewModel.products.removeObserver(apiProducts)
        }
    }

    @Test
    fun givenResponseNotOk_whenCallLoadProducts_shouldReturnException() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"

            coEvery { useCase.findListProducts() }.throws(RuntimeException(errorMessage))

            val viewModel = GloboProductsViewModel(scope, useCase)
            viewModel.products.observeForever(apiProducts)

            viewModel.loadProducts()

            coVerify { useCase.findListProducts() }

            coVerify {
                apiProducts.onChanged(
                        Resource.loading(
                                null
                        )
                )
            }

            coVerify {
                apiProducts.onChanged(
                        Resource.error(
                                RuntimeException(errorMessage).toString(),
                                null
                        )
                )
            }

            viewModel.products.removeObserver(apiProducts)
        }
    }

    @Test
    fun givenResponseNotOk_whenCallLoadProducts_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductModelUi>> = Either.Left("Error")
            coEvery { useCase.findListProducts() } returns response

            val viewModel = GloboProductsViewModel(scope, useCase)
            viewModel.products.observeForever(apiProducts)

            viewModel.loadProducts()

            coVerify { useCase.findListProducts() }

            coVerify {
                apiProducts.onChanged(
                    Resource.loading(
                        null
                    )
                )
            }

            coVerify {
                apiProducts.onChanged(
                    Resource.error(
                        "Error",
                        null
                    )
                )
            }

            viewModel.products.removeObserver(apiProducts)
        }
    }
}