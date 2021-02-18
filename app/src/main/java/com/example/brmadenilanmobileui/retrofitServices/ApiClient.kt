package com.example.brmadenilanmobileui.retrofitServices

import com.example.brmadenilanmobileui.interceptors.NetworkInterceptor
import com.example.brmadenilanmobileui.interceptors.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        fun <T> buildService(
            baseUrl: String,
            retrofitService: Class<T>,
            existInterceptor: Boolean = false
        ): T {
            var clientBuilder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(NetworkInterceptor());
            if (existInterceptor)
                clientBuilder.addInterceptor(TokenInterceptor());

            return Retrofit.Builder().baseUrl(baseUrl).client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create()).build().create(retrofitService);
        }
    }
}