package com.nicco.api.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(
    val id: Int = 0,
    val createdAt: String = "",
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val price: String = "",
    val color: String = ""
)

@JsonClass(generateAdapter = true)
data class ProductResponseBff(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val price: String = ""
)