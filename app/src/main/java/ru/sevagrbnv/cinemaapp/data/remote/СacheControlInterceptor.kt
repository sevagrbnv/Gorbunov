package ru.sevagrbnv.cinemaapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class СacheControlInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithCacheControl = originalRequest.newBuilder()
            .header("Cache-Control", "public, max-age=300") // Настройте согласно своим требованиям
            .build()
        return chain.proceed(requestWithCacheControl)
    }
}