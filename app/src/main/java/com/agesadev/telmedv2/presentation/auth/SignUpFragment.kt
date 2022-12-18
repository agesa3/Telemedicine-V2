package com.agesadev.telmedv2.presentation.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.agesadev.telmedv2.R
import com.agesadev.telmedv2.databinding.FragmentSignUpBinding
import com.agesadev.telmedv2.presentation.auth.login.AuthViewModel
import com.agesadev.telmedv2.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _signUpBinding: FragmentSignUpBinding? = null
    private val signUpBinding get() = _signUpBinding

    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _signUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        signUpBinding?.signUpButton?.setOnClickListener {
            authViewModel.signUp(
                signUpBinding?.signUpEmail?.text.toString(),
                signUpBinding?.signUpPassword?.text.toString()
            )
        }
        return signUpBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.signUpFlow.collectLatest { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            Log.d("Signup", "onViewCreated: ${resource.exception}")
                        }
                        Resource.Loading -> {
                            Log.d("Signup", "onViewCreated: Loading")
                        }
                        is Resource.Success -> {
                            Log.d("Signup", "onViewCreated: ${resource.data}")
                        }
                        else -> {
                            Log.d("Signup", "onViewCreated: else")
                        }
                    }
                }
            }
        }
    }
}