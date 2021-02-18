package com.example.brmadenilanmobileui.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brmadenilanmobileui.apiServices.TokenService
import com.example.brmadenilanmobileui.models.StatefulViewModel
import com.example.brmadenilanmobileui.utility.LoadingState
import kotlinx.coroutines.launch

class LaunchActivityViewModel : StatefulViewModel() {


    fun tokenCheck(): LiveData<Boolean> {
        loadingState.value = LoadingState.Loading;
        var status = MutableLiveData<Boolean>();
        viewModelScope.launch {
            var response = TokenService.checkToken();
            status.value = response.isSuccessful;
            loadingState.value = LoadingState.Loaded;
            if (!response.isSuccessful) errorState.value = response.fail;
        }
        return status;
    }
}