package com.nicco.business.mapper

import com.nicco.business.model.ProductModelUi
import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff

interface ProductMapper {
    fun mapToUi(productsResponse: List<ProductResponse>): List<ProductModelUi>
    fun mapToBffUi(productsResponse: List<ProductResponseBff>): List<ProductModelUi>
}

class ProductMapperImpl : ProductMapper {
    override fun mapToUi(productsResponse: List<ProductResponse>): List<ProductModelUi> =
        productsResponse.map {
            ProductModelUi(
                it.id,
                it.createdAt,
                it.name,
                it.image,
                it.description,
                it.price,
                it.color
            )
        }

    override fun mapToBffUi(productsResponse: List<ProductResponseBff>): List<ProductModelUi> =
        productsResponse.map {
            ProductModelUi(
                id = it.id,
                name = it.name,
                description = it.description,
                price = it.price
            )
        }
}