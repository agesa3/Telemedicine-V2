package com.agesadev.telmedv2.presentation.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.agesadev.telmedv2.R
import com.agesadev.telmedv2.databinding.FragmentLoginBinding
import com.agesadev.telmedv2.utils.Resource
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
        return loginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.loginFlow.collectLatest { resource ->
                    when (resource) {
                        is Resource.Error -> {

                        }
                        Resource.Loading -> {

                        }
                        is Resource.Success -> {

                        }
                        null -> TODO()
                    }

                }
            }
        }
    }
}