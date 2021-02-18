package com.example.brmadenilanmobileui.retrofitServices

import com.example.brmadenilanmobileui.models.Introspect
import com.example.brmadenilanmobileui.models.Token
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface IRetrofitTokenService {
    @FormUrlEncoded
    @POST("connect/token")
    suspend fun getTokenWhitClientCredentials(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): Response<Token>

    @FormUrlEncoded
    @POST("connect/token")
    suspend fun checkToken(
        @Field("token") token: String,
        @Header("Authorization") authorization: String
    ): Response<Introspect>

    @FormUrlEncoded
    @POST("connect/token")
    fun refreshToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("refresh_token") refreshToken: String
        ): Call<Token>
}