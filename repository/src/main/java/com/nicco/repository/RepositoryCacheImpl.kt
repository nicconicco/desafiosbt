package com.nicco.repository

import com.nicco.api.domain.ProductResponse

class RepositoryCacheImpl : RepositoryCache {
    private var cache: List<ProductResponse>? = null

    override fun hasCache() = !cache.isNullOrEmpty()

    override fun getCache(): List<ProductResponse>? = cache

    override fun saveCache(list: List<ProductResponse>) {
        cache = list
    }
}