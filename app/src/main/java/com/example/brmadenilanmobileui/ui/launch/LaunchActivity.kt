package com.example.brmadenilanmobileui.ui.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brmadenilanmobileui.R
import com.example.brmadenilanmobileui.ui.auth.AuthActivity
import com.example.brmadenilanmobileui.ui.user.UserActivity

class LaunchActivity : AppCompatActivity() {
    lateinit var viewModel: LaunchActivityViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        viewModel = ViewModelProvider(this).get(LaunchActivityViewModel::class.java);

        viewModel.tokenCheck().observe(this, Observer {
            var intent = when (it) {
                true -> Intent(this@LaunchActivity, UserActivity::class.java);
                else -> Intent(this@LaunchActivity, AuthActivity::class.java);
            }
            startActivity(intent);
        });
    }
}