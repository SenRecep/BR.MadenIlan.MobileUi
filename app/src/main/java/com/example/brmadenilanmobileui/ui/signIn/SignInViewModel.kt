package com.example.brmadenilanmobileui.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brmadenilanmobileui.apiServices.AuthService
import com.example.brmadenilanmobileui.models.StatefulViewModel
import com.example.brmadenilanmobileui.models.UserSignIn
import com.example.brmadenilanmobileui.utility.LoadingState
import kotlinx.coroutines.launch

class SignInViewModel : StatefulViewModel() {
    fun signIn(userSignIn: UserSignIn):LiveData<Boolean>{
        loadingState.value=LoadingState.Loading;
        var status= MutableLiveData<Boolean>();
        viewModelScope.launch {
            var apiResponse=AuthService.signIn(userSignIn);
            status.value=apiResponse.isSuccessful;
            if (!apiResponse.isSuccessful)
                errorState.value=apiResponse.fail;
            loadingState.value= LoadingState.Loaded;
        }
        return  status;
    }
}