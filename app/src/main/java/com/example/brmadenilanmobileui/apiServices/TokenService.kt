package com.example.brmadenilanmobileui.apiServices

import android.content.Context
import com.example.brmadenilanmobileui.BuildConfig
import com.example.brmadenilanmobileui.consts.ApiConsts
import com.example.brmadenilanmobileui.models.ApiResponse
import com.example.brmadenilanmobileui.models.Introspect
import com.example.brmadenilanmobileui.models.Token
import com.example.brmadenilanmobileui.retrofitServices.ApiClient
import com.example.brmadenilanmobileui.retrofitServices.IRetrofitTokenService
import com.example.brmadenilanmobileui.utility.GlobalApp
import com.example.brmadenilanmobileui.utility.HelperService
import com.google.gson.Gson
import okhttp3.Credentials

class TokenService() {
    companion object {
        private var retrofitTokenServiceWithoutInterceptor =
            ApiClient.buildService(ApiConsts.authBaseUrl, IRetrofitTokenService::class.java, false);

        suspend fun getTokenWhitClientCredentials(): ApiResponse<Token> {
            try {

                var response = retrofitTokenServiceWithoutInterceptor.getTokenWhitClientCredentials(
                    BuildConfig.ClientId_CC,
                    BuildConfig.ClientSecret_CC,
                    ApiConsts.clientCredentialGrantType
                );
                if (!response.isSuccessful) return HelperService.handleApiError(response);
                return ApiResponse(true, response.body() as Token);
            } catch (ex: Exception) {
                return HelperService.handleException(ex);
            }
        }

        fun refreshToken(refreshToken: String): ApiResponse<Token> {
            return try {
                var response = retrofitTokenServiceWithoutInterceptor.refreshToken(
                    BuildConfig.ClientId_ROP,
                    BuildConfig.ClientSecret_ROP,
                    ApiConsts.refreshTokenCredentialGrantType,
                    refreshToken
                ).execute();

                if (!response.isSuccessful) HelperService.handleApiError(response);
                else ApiResponse(true, response.body() as Token);
            } catch (ex: Exception) {
                HelperService.handleException(ex);
            }
        }

        suspend fun checkToken(): ApiResponse<Unit> {
            try {
                var preference =
                    GlobalApp.getAppContext()
                        .getSharedPreferences("ApiToken", Context.MODE_PRIVATE);
                var tokenString: String? =
                    preference.getString("token", null) ?: return ApiResponse(false);
                var token: Token = Gson().fromJson(tokenString, Token::class.java);
                var authorization: String =
                    Credentials.basic(BuildConfig.BasicUserName, BuildConfig.BasicPassword);
                var response =
                    retrofitTokenServiceWithoutInterceptor.checkToken(
                        token.AccessToken,
                        authorization
                    );
                if (!response.isSuccessful) return HelperService.handleApiError(response);
                var introspect = response.body() as Introspect;
                if (!introspect.Active) return HelperService.handleApiError(response);
                return ApiResponse(true);
            } catch (ex: Exception) {
                return HelperService.handleException(ex);
            }
        }
    }
}