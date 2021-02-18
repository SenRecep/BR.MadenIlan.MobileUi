package com.example.brmadenilanmobileui.interceptors

import android.content.Context
import android.util.Log
import com.example.brmadenilanmobileui.apiServices.TokenService
import com.example.brmadenilanmobileui.models.Token
import com.example.brmadenilanmobileui.utility.GlobalApp
import com.example.brmadenilanmobileui.utility.HelperService
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var token: Token? = null;
        var request = chain.request();
        var preference =
            GlobalApp.getAppContext().getSharedPreferences("ApiToken", Context.MODE_PRIVATE);
        var tokenString = preference.getString("token", null);
        if (tokenString != null) {
            Log.i("OkHttp", "token SharedPreferences'dan okundu");
            token = Gson().fromJson(tokenString, Token::class.java);
            request = request.newBuilder().addHeader("Authorization", "Bearer ${token.AccessToken}")
                .build();
        }
        var response = chain.proceed(request);
        if (response.code == 401) {
            Log.i("OkHttp", "token geçersiz [401]");
            if (token != null) {
                var apiResponse = TokenService.refreshToken(token.RefreshToken);
                if (apiResponse.isSuccessful) {
                    HelperService.saveTokenSharedPreference(apiResponse.success!!);
                    var newToken = apiResponse.success!!;
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer ${newToken.AccessToken}")
                        .build();
                    response = chain.proceed(request);
                } else {
                    //todo Login Ekranina dondurulecek
                }
            }
        }
        return response;
    }
}