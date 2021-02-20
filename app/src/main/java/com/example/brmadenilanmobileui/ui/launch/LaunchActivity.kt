package com.example.brmadenilanmobileui.ui.launch

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brmadenilanmobileui.R
import com.example.brmadenilanmobileui.ui.auth.AuthActivity
import com.example.brmadenilanmobileui.ui.user.UserActivity
import com.example.brmadenilanmobileui.utility.LoadingState
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {
    lateinit var viewModel: LaunchActivityViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        viewModel = ViewModelProvider(this).get(LaunchActivityViewModel::class.java);

        var scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2f);
        var scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f);
        var animator = ObjectAnimator.ofPropertyValuesHolder(image_company_logo, scaleX, scaleY);

        animator.repeatMode = ObjectAnimator.REVERSE;
        animator.repeatCount = Animation.INFINITE;
        animator.duration = 1000;

        viewModel.loadingState.observe(this, Observer {
            when (it) {
                LoadingState.Loading -> animator.start();
                LoadingState.Loaded -> animator.cancel();
            }
        });

        viewModel.tokenCheck();
        viewModel.isSuccessful.observe(this, Observer {
            var intent = when (it) {
                true -> Intent(this@LaunchActivity, UserActivity::class.java);
                else -> Intent(this@LaunchActivity, AuthActivity::class.java);
            }
            startActivity(intent);
            finish();
        });
    }
}