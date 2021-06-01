package com.nicco.api

import android.content.Context
import android.util.Log
import com.nicco.api.URL.url
import com.nicco.api.domain.ProductResponse
import com.nicco.api.domain.ProductResponseBff
import com.nicco.api.okhttp.GloboOkHttp
import com.nicco.api.utils.Either
import com.nicco.api.utils.Utils.getJsonDataFromAsset
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.Exception

class ApiImpl(
        private val globoOkHttp: GloboOkHttp,
        private val context: Context
) : Api {
    override suspend fun findListProducts(): Either<String, List<ProductResponse>> {
        val response = globoOkHttp.getRequest(url)
        val adapter: JsonAdapter<List<ProductResponse>> = moshiSetup()

        return try {
            val result = adapter.fromJson(response)

            Either.Right(
                    result!!
            )
        } catch (e: Exception) {
            Either.Left("Erro")
        }
    }

    override fun loadBffProducts(): Either<String, List<ProductResponseBff>> {
        val jsonFileString = getJsonDataFromAsset(context, "bff.json")
        val adapter: JsonAdapter<List<ProductResponseBff>> = moshiSetup()

        jsonFileString?.apply {
            Log.i("data", jsonFileString)
        }

        return try {
            val result = adapter.fromJson(jsonFileString)

            Either.Right(
                    result!!
            )
        } catch (e: Exception) {
            Either.Left("Erro")
        }
    }

    private inline fun <reified T>moshiSetup(): JsonAdapter<List<T>> {
        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, T::class.java)
        return moshi.adapter(listType)
    }
}