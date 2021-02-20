package com.example.brmadenilanmobileui.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brmadenilanmobileui.apiServices.TokenService
import com.example.brmadenilanmobileui.models.StatefulViewModel
import com.example.brmadenilanmobileui.utility.HelperService
import com.example.brmadenilanmobileui.utility.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LaunchActivityViewModel : StatefulViewModel() {

    var isSuccessful = MutableLiveData<Boolean>();

    private fun refreshTokenCheck() {
        CoroutineScope(Dispatchers.IO).launch {
            var token = HelperService.getTokenSharedPreference();
            if (token!=null){
                var response= TokenService.refreshToken(token.RefreshToken);
                if (response.isSuccessful)
                    HelperService.saveTokenSharedPreference(response.success!!);
                isSuccessful.postValue(response.isSuccessful);
            }
            else
                isSuccessful.postValue(false);
            loadingState.postValue(LoadingState.Loaded);
        }
    }

    fun tokenCheck() {
        loadingState.value = LoadingState.Loading;
        var status = MutableLiveData<Boolean>();
        viewModelScope.launch {
            var response = TokenService.checkToken();
            status.value = response.isSuccessful;
            loadingState.value = LoadingState.Loaded;
            if (response.isSuccessful) {
                loadingState.value = LoadingState.Loaded;
                isSuccessful.value=true;
            } else {
                errorState.value = response.fail!!;
                refreshTokenCheck();
            }
        }
    }
}