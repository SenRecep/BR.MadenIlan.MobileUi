package com.example.brmadenilanmobileui.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brmadenilanmobileui.utility.IViewModelState
import com.example.brmadenilanmobileui.utility.LoadingState

open class StatefulViewModel:ViewModel(),IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>();
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>();
}