package com.nicco.business.mapper

import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff
import org.junit.Test

object FactoryMapper {
    fun build(): List<ProductResponse> {
        val listProductResponse = arrayListOf<ProductResponse>()
        listProductResponse.add(ProductResponse(name = "Test", description = "This is a real test"))
        listProductResponse.add(
            ProductResponse(
                name = "Test1",
                description = "This is a real test1"
            )
        )
        listProductResponse.add(
            ProductResponse(
                name = "Test3",
                description = "This is a real test3"
            )
        )

        return listProductResponse
    }

    fun buildBff(): List<ProductResponseBff> {
        val productResponseBff = arrayListOf<ProductResponseBff>()
        productResponseBff.add(
            ProductResponseBff(
                name = "Test",
                description = "This is a real test"
            )
        )
        return productResponseBff
    }
}

class ProductMapperTest {

    @Test
    fun givenproductMapper_whenCallMapToUi_ShouldReturnListOfProductModelUi() {
        val list = FactoryMapper.build()
        val result = ProductMapperImpl().mapToUi(list)
        assert(result.isNotEmpty())
        assert(result.size == 3)
        assert(result[0].name == "Test")
        assert(result[0].description == "This is a real test")
        assert(result[0].id == 0)
        assert(result[0].name == "Test")
    }

    @Test
    fun givenproductMapper_whenCallMapToBffUi_ShouldReturnListOfProductModelUi() {
        val list = FactoryMapper.buildBff()
        val result = ProductMapperImpl().mapToBffUi(list)

        assert(result.isNotEmpty())
        assert(result.size == 1)
        assert(result[0].name == "Test")
        assert(result[0].color == "")
        assert(result[0].createdAt == "")
        assert(result[0].price == "")
    }
}