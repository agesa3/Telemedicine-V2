package com.agesadev.telmedv2.presentation.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.agesadev.telmedv2.R
import com.agesadev.telmedv2.databinding.FragmentSignUpBinding
import com.agesadev.telmedv2.presentation.auth.AuthViewModel
import com.agesadev.telmedv2.utils.Resource
import com.google.android.material.snackbar.Snackbar
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
        signUpBinding?.signUpBtn?.setOnClickListener {
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
                authViewModel.authStateSignUp.collectLatest { authState ->
                    when {
                        authState.isLoading -> {
                            signUpBinding?.signUpProgressBar?.visibility = View.VISIBLE
                        }
                        authState.user != null -> {
                            findNavController().navigate(R.id.homeFragment)
                            signUpBinding?.signUpProgressBar?.visibility = View.GONE
                        }
                        authState.error.isNotEmpty() -> {
                            signUpBinding?.signUpProgressBar?.visibility = View.GONE
                            Snackbar.make(
                                requireView(),
                                authState.error,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
    }
}

