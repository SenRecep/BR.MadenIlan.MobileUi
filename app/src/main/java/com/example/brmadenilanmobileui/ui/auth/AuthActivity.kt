package com.example.brmadenilanmobileui.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.brmadenilanmobileui.R
import com.example.brmadenilanmobileui.ui.signIn.SignInFragment
import com.example.brmadenilanmobileui.ui.signUp.SignUpFragment
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        var pagerAdapter=ScreenSlidePagerAdapter(this);
        pagerAdapter.addFragment(SignInFragment());
        pagerAdapter.addFragment(SignUpFragment());
        AuthViewPager.adapter=pagerAdapter;
    }
}