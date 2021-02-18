package com.example.brmadenilanmobileui.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brmadenilanmobileui.apiServices.AuthService
import com.example.brmadenilanmobileui.models.StatefulViewModel
import com.example.brmadenilanmobileui.models.UserSignUp
import com.example.brmadenilanmobileui.utility.LoadingState
import kotlinx.coroutines.launch

class SignUpViewModel : StatefulViewModel(){
    fun signUp(userSignUp: UserSignUp):LiveData<Boolean>{
        var status = MutableLiveData<Boolean>();
        loadingState.value=LoadingState.Loading;
        viewModelScope.launch {
            var response = AuthService.signUp(userSignUp);
            status.value=response.isSuccessful;
            loadingState.value=LoadingState.Loaded;
            if(!response.isSuccessful) errorState.value=response.fail;
        }
        return status;
    }
}