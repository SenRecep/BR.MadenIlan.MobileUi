package com.example.brmadenilanmobileui.utility

import androidx.lifecycle.MutableLiveData
import com.example.brmadenilanmobileui.models.ApiError

interface IViewModelState {
    var loadingState: MutableLiveData<LoadingState>;
    var errorState: MutableLiveData<ApiError>;
}