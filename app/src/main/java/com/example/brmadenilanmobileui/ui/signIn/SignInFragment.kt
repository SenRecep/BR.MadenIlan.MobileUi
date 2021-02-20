package com.example.brmadenilanmobileui.ui.signIn

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.brmadenilanmobileui.R
import com.example.brmadenilanmobileui.models.UserSignIn
import com.example.brmadenilanmobileui.ui.user.UserActivity
import com.example.brmadenilanmobileui.utility.HelperService
import com.example.brmadenilanmobileui.utility.LoadingState
import kotlinx.android.synthetic.main.sign_in_fragment.*
import kotlinx.android.synthetic.main.sign_in_fragment.view.*
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java);
        var root = inflater.inflate(R.layout.sign_in_fragment, container, false);

        viewModel.errorState.observe(viewLifecycleOwner, Observer {
            HelperService.showErrorMassageByToast(it);
        });

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoadingState.Loading -> button_signin_register.startAnimation();
                LoadingState.Loaded -> button_signin_register.revertAnimation();
            }
        });

        root.button_signin_register.setOnClickListener {
            val userSignIn = UserSignIn(
                text_signin_username.editText?.text.toString(),
                text_signin_password.editText?.text.toString()
            );
            viewModel.signIn(userSignIn).observe(viewLifecycleOwner, Observer {
                if (it) {
                    var intent= Intent(requireActivity(),UserActivity::class.java);
                    startActivity(intent);
                    requireActivity().finish();
                }
            });
        };
        return root;
    }
}