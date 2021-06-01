package com.nicco.api.okhttp

import okhttp3.*
import okio.BufferedSource

interface GloboOkHttp {
    fun getRequest(url: String): BufferedSource?
}

class GloboOkHttpImpl(
    private val client: OkHttpClient
): GloboOkHttp {
    override fun getRequest(url: String): BufferedSource? {
        var result: BufferedSource? = null

        try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            result = response.body?.source()
        } catch (err: Error) {
            print("Error when executing get request: " + err.localizedMessage)
        }

        return result
    }
}