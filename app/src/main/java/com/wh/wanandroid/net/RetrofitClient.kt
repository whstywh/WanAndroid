package com.wh.wanandroid.net

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor() {

    private var retrofit: Retrofit

    companion object {
        val instance: RetrofitClient by lazy { RetrofitClient() }
    }

    init {
        retrofit = Retrofit.Builder()
            .client(initOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiService.base_url)
            .build()
    }

    private fun initOkhttpClient() = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor())
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    fun create(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}