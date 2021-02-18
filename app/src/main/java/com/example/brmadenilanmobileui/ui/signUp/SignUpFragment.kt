package com.example.brmadenilanmobileui.ui.signUp

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.brmadenilanmobileui.R
import com.example.brmadenilanmobileui.models.UserSignUp
import com.example.brmadenilanmobileui.utility.HelperService
import com.example.brmadenilanmobileui.utility.LoadingState
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        var fragmentView = inflater.inflate(R.layout.sign_up_fragment, container, false);
        var viewPager = requireActivity().findViewById<ViewPager2>(R.id.AuthViewPager);

        viewModel.errorState.observe(viewLifecycleOwner, Observer {
            HelperService.showErrorMassageByToast(it);
        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoadingState.Loading -> button_signup_register.startAnimation();
                LoadingState.Loaded -> button_signup_register.revertAnimation();
            }
        });

        fragmentView.button_signup_register.setOnClickListener {
            var userSignUp = UserSignUp(
                text_signup_username.editText?.text.toString(),
                text_signup_email.editText?.text.toString(),
                text_signup_password.editText?.text.toString()
            );
            viewModel.signUp(userSignUp).observe(viewLifecycleOwner, Observer {
                when (it) {
                    true -> {
                        viewPager.currentItem = 0;
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000);
                            HelperService.onAlertDialogJustOk(fragmentView,"Kayıt işlemi başarı ile gerçekleştirildi. Kullanıcı adı ve parola ile giriş yapabilirsiniz.");
                        };
                    }
                    else -> {
                    }
                }
            })
        }
        return fragmentView;
    }
}