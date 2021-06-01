package com.nicco.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nicco.datasource.Datasource
import com.nicco.api.domain.ProductResponse
import com.nicco.api.utils.Either
import com.nicco.repository.utils.TestCoroutineRule
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    val datasource = mockk<Datasource>()
    lateinit var repository: Repository

    @Test
    fun whenRepository_callsFindListProduct_shouldReturnListOfProducts() {
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Right(emptyList())
            coEvery {
                datasource.findListProducts()
            } returns response

            val cache = mockk<RepositoryCache>()
            every { cache.saveCache(emptyList()) } just runs

            repository = RepositoryImpl(datasource, cache)
            val result = repository.findListProducts(true)

            assert(result.isRight)
            coVerify { datasource.findListProducts() }
        }
    }

    @Test
    fun whenRepository_callsFindListProduct_shouldReturnCacheListEmpty() {
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Right(emptyList())
            coEvery {
                datasource.findListProducts()
            } returns response

            val cache = mockk<RepositoryCache>()

            every { cache.saveCache(emptyList()) } just runs
            every { cache.hasCache() } returns true
            every { cache.getCache()!! } returns emptyList()

            repository = RepositoryImpl(datasource, cache)
            val result = repository.findListProducts(false)

            assert((result as Either.Right).b.isEmpty())
            assert(result.isRight)
        }
    }

    @Test
    fun whenRepository_callsFindListProduct_shouldReturn() {
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Right(emptyList())
            coEvery {
                datasource.findListProducts()
            } returns response

            val cache = mockk<RepositoryCache>()

            every { cache.saveCache(emptyList()) } just runs
            every { cache.hasCache() } returns true
            every { cache.getCache()!! } returns emptyList()

            repository = RepositoryImpl(datasource, cache)
            val result = repository.findListProducts(false)

            assert((result as Either.Right).b.isEmpty())
            assert(result.isRight)
        }
    }

    @Test
    fun whenRepository_callsFindListProduct_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val response: Either<String, List<ProductResponse>> = Either.Left("Erro")

            coEvery {
                datasource.findListProducts()
            } returns response

            val cache = mockk<RepositoryCache>()
            repository = RepositoryImpl(datasource, cache)

            val result = repository.findListProducts(true)

            assert(result.isLeft)
            coVerify { datasource.findListProducts() }
        }
    }

    @Test
    fun whenRepository_callsReloadListToExpensiveProducts_shouldReturnListOfProducts() {
        testCoroutineRule.runBlockingTest {
            val cache = prepareScenarioExpensiveProducts()

            repository = RepositoryImpl(datasource, cache)

            val result = repository.reloadListToExpensiveProducts()

            assert(result.isRight)
        }
    }

    @Test
    fun whenRepository_callsReloadListToExpensiveProducts_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val cache = prepareScenarioExpensiveError()

            repository = RepositoryImpl(datasource, cache)

            val result = repository.reloadListToExpensiveProducts()

            assert(result.isLeft)
            assert((result as Either.Left).a == "Lista nao carregada")
        }
    }

    @Test
    fun whenRepository_callsReloadListToExpensiveProducts_shouldCheckList() {
        testCoroutineRule.runBlockingTest {
            val cache = prepareScenarioExpensiveProductsInOrder()

            repository = RepositoryImpl(datasource, cache)

            val result = repository.reloadListToExpensiveProducts()

            assert(result.isRight)
            assert((result as Either.Right).b.isNotEmpty())

            val list = result.b

            assert(list[0].price == "90")
            assert(list[1].price == "45")
            assert(list[2].price == "44")
            assert(list[3].price == "30")
        }
    }

    @Test
    fun whenRepository_callsReloadListToCheaperProducts_shouldCheckList() {
        testCoroutineRule.runBlockingTest {
            val cache = prepareScenarioExpensiveProductsInOrder()

            repository = RepositoryImpl(datasource, cache)

            val result = repository.reloadListToCheaperProducts()

            assert(result.isRight)
            assert((result as Either.Right).b.isNotEmpty())

            val list = result.b

            assert(list[0].price == "30")
            assert(list[1].price == "44")
            assert(list[2].price == "45")
            assert(list[3].price == "90")
        }
    }

    private fun prepareScenarioExpensiveProductsInOrder(): RepositoryCache {
        val list: MutableList<ProductResponse> = mutableListOf()

        list.add(ProductResponse(id = 1, price = "30"))
        list.add(ProductResponse(id = 1, price = "45"))
        list.add(ProductResponse(id = 1, price = "44"))
        list.add(ProductResponse(id = 1, price = "90"))

        val response: Either<String, List<ProductResponse>> = Either.Right(emptyList())
        coEvery {
            datasource.findListProducts()
        } returns response

        val cache = mockk<RepositoryCache>()

        every {
            cache.hasCache()
        } returns true

        every {
            cache.getCache()
        } returns list

        return cache
    }

    private fun prepareScenarioExpensiveError(): RepositoryCache {
        val response: Either<String, List<ProductResponse>> = Either.Left("Lista nao carregada")

        coEvery {
            datasource.findListProducts()
        } returns response

        val cache = mockk<RepositoryCache>()

        every {
            cache.hasCache()
        } returns false

        return cache
    }

    private fun prepareScenarioExpensiveProducts(): RepositoryCache {
        val response: Either<String, List<ProductResponse>> = Either.Right(emptyList())
        coEvery {
            datasource.findListProducts()
        } returns response

        val cache = mockk<RepositoryCache>()

        every {
            cache.hasCache()
        } returns true

        every {
            cache.getCache()
        } returns emptyList()

        return cache
    }
}