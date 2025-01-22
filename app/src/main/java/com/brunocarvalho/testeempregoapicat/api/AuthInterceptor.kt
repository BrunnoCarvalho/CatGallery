package com.brunocarvalho.testeempregoapicat.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val construtorRequisicao = chain.request().newBuilder()
            .addHeader("Authorization", "Client-ID ${RetrofitService.clientID}")
            .build()

        return chain.proceed(construtorRequisicao)
    }
}