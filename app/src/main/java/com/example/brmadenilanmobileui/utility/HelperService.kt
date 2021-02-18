package com.example.brmadenilanmobileui.utility

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.brmadenilanmobileui.R
import com.example.brmadenilanmobileui.exceptions.OfflineException
import com.example.brmadenilanmobileui.models.ApiError
import com.example.brmadenilanmobileui.models.ApiResponse
import com.example.brmadenilanmobileui.models.Token
import com.google.gson.Gson
import retrofit2.Response
import java.lang.StringBuilder

class HelperService {
    companion object {

        fun <T> handleException(ex: Exception): ApiResponse<T> {
            return when (ex) {
                is OfflineException -> {
                    val ex_massage =
                        arrayListOf(GlobalApp.getAppContext().resources.getString(R.string.ex_no_exception));
                    var apiError: ApiError = ApiError(ex_massage, 408, true);
                    ApiResponse(false, fail = apiError);
                }
                else -> {
                    val ex_massage =
                        arrayListOf(GlobalApp.getAppContext().resources.getString(R.string.ex_common_error));
                    var apiError: ApiError = ApiError(ex_massage, 408, true);
                    ApiResponse(false, fail = apiError);
                }
            }
        }

        fun saveTokenSharedPreference(token: Token) {
            var preference =
                GlobalApp.getAppContext().getSharedPreferences("ApiToken", Context.MODE_PRIVATE);
            var editor = preference.edit();
            editor.putString("token", Gson().toJson(token));
            editor.apply();
        }

        fun <T1, T2> handleApiError(response: Response<T1>): ApiResponse<T2> {
            var apiError: ApiError? = null;
            var errorBody = response.errorBody();
            if (errorBody != null) {
                var body = errorBody.string();
                apiError = Gson().fromJson(body, ApiError::class.java);
            }
            return ApiResponse(false, fail = apiError);
        }

        fun onAlertDialogJustOk(view: View, massage: String) {
            var builder = AlertDialog.Builder(view.context);
            builder.setMessage(massage);
            builder.setPositiveButton("Tamam") { _, _ -> };
            builder.show();
        }

        fun showErrorMassageByToast(apiError: ApiError?) {
            if (apiError==null) return;
            var errorBuilder = StringBuilder();
            if (apiError.IsShow)
                for (error in apiError.Errors)
                    errorBuilder.append("$error\n");
            Toast.makeText(GlobalApp.getAppContext(), errorBuilder.toString(), Toast.LENGTH_LONG)
                .show();

        }
    }
}