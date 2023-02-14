package com.agesadev.telmedv2.presentation.auth

import androidx.lifecycle.ViewModelProvider
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
import com.agesadev.telmedv2.databinding.FragmentLoginBinding
import com.agesadev.telmedv2.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private var _loginBinding: FragmentLoginBinding? = null
    private val loginBinding get() = _loginBinding

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _loginBinding =
            FragmentLoginBinding.inflate(inflater, container, false)
        loginBinding?.loginBtn?.setOnClickListener {
            authViewModel.login(
                loginBinding?.loginEmail?.text.toString(),
                loginBinding?.loginPassword?.text.toString()
            )
        }
        return loginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.authStateLogin.collectLatest { state ->
                    when {
                        state.isLoading -> {
                            loginBinding?.loginProgressBar?.visibility = View.VISIBLE
                        }
                        state.error.isNotEmpty() -> {
                            loginBinding?.loginProgressBar?.visibility = View.GONE
                            Snackbar.make(
                                requireView(),
                                state.error,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        state.user != null -> {
                            loginBinding?.loginProgressBar?.visibility = View.GONE
                            findNavController().navigate(R.id.homeFragment)
                        }
                    }
                }
            }
        }
    }
}