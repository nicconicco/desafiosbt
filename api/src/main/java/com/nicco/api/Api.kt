package com.nicco.api

import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff
import com.nicco.api.utils.Either

object URL {
    val url = "https://60885258a6f4a30017426369.mockapi.io/products"
}
interface Api {
    suspend fun findListProducts(): Either<String, List<ProductResponse>>
    fun loadBffProducts(): Either<String, List<ProductResponseBff>>
}