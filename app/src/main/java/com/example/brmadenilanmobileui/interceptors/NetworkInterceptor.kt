package com.example.brmadenilanmobileui.interceptors

import com.example.brmadenilanmobileui.exceptions.OfflineException
import com.example.brmadenilanmobileui.utility.LiveNetworkListener
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!LiveNetworkListener.isConnected())
            throw  OfflineException("Internet Bağlantısı Yok");

        var request = chain.request();
        return chain.proceed(request);
    }
}