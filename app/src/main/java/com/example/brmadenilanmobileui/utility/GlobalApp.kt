package com.example.brmadenilanmobileui.utility

import android.app.Application
import android.content.Context

class GlobalApp : Application() {
    companion object{
        private  lateinit var mContext:Context;
        fun getAppContext ():Context{
            return mContext;
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext=this;
    }
}