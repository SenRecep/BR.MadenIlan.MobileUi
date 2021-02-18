package com.example.brmadenilanmobileui.apiServices

import com.example.brmadenilanmobileui.BuildConfig
import com.example.brmadenilanmobileui.consts.ApiConsts
import com.example.brmadenilanmobileui.models.ApiResponse
import com.example.brmadenilanmobileui.models.Token
import com.example.brmadenilanmobileui.models.UserSignIn
import com.example.brmadenilanmobileui.models.UserSignUp
import com.example.brmadenilanmobileui.retrofitServices.ApiClient
import com.example.brmadenilanmobileui.retrofitServices.IRetrofitAuthService
import com.example.brmadenilanmobileui.utility.HelperService

class AuthService {
    companion object {
        private var retrofitAuthServiceWithoutInterceptor =
            ApiClient.buildService(ApiConsts.authBaseUrl, IRetrofitAuthService::class.java, false);

        suspend fun signUp(userSignUp: UserSignUp): ApiResponse<Unit> {
            try {
                var tokenResponse = TokenService.getTokenWhitClientCredentials();
                if (!tokenResponse.isSuccessful) return ApiResponse(false,fail = tokenResponse.fail);
                var token = tokenResponse.success!!;
                var signUpResponse = retrofitAuthServiceWithoutInterceptor.signUp(
                    userSignUp,
                    "bearer ${token.AccessToken}"
                );
                if (!signUpResponse.isSuccessful) return HelperService.handleApiError(signUpResponse);
                return ApiResponse(true);
            } catch (ex: Exception) {
                return HelperService.handleException(ex);
            }

        }

        suspend fun signIn(userSignIn: UserSignIn): ApiResponse<Unit> {
            try {
                var response = retrofitAuthServiceWithoutInterceptor.signIn(
                    BuildConfig.ClientId_ROP,
                    BuildConfig.ClientSecret_ROP,
                    ApiConsts.resourceOwnerPasswordCredentialGrantType,
                    userSignIn.UserName,
                    userSignIn.Password
                );
                if (!response.isSuccessful) return HelperService.handleApiError(response);
                var token = response.body() as Token;
                HelperService.saveTokenSharedPreference(token);
                return ApiResponse(true);
            } catch (ex: Exception) {
                return HelperService.handleException(ex);
            }

        }
    }
}