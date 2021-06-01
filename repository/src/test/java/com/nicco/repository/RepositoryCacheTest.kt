package com.nicco.repository

import com.nicco.api.domain.ProductResponse
import junit.framework.Assert.assertFalse
import org.junit.Test

class RepositoryCacheTest {

    @Test
    fun whenRepositoryCache_callsHasCache_shouldReturnTrue() {
        val repositoryCache: RepositoryCache
        repositoryCache = RepositoryCacheImpl()
        val list: MutableList<ProductResponse> = mutableListOf()
        list.add(ProductResponse())

        repositoryCache.saveCache(list)
        assert(repositoryCache.hasCache())
    }

    @Test
    fun whenRepositoryCache_callsHasCache_shouldReturnFalseBecauseEmptyList() {
        val repositoryCache: RepositoryCache
        repositoryCache = RepositoryCacheImpl()

        repositoryCache.saveCache(emptyList())
        assertFalse(repositoryCache.hasCache())
        assert(repositoryCache.getCache()!!.isEmpty())
    }

    @Test
    fun whenRepositoryCache_callsHasCache_shouldReturnFalseBecauseNotInitialized() {
        val repositoryCache: RepositoryCache
        repositoryCache = RepositoryCacheImpl()

        assertFalse(repositoryCache.hasCache())
        assert(repositoryCache.getCache() == null)
    }
}