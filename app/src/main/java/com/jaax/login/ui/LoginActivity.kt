package com.jaax.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.jaax.login.data.LoginMVP
import com.jaax.login.data.LoginPresenter
import com.jaax.login.databinding.ActivityLoginBinding
import com.jaax.login.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginMVP.View {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.btnLogin.setOnClickListener {
            presenter.loginButtonClicked()
        }
    }

    override fun getUsername(): String {
        return binding.etUsername.text.toString()
    }

    override fun getPassword(): String {
        return binding.etPassword.text.toString()
    }

    override fun grantAccess(grant: Boolean) {
        if(grant) {
            Toast.makeText(this, "TOKEN EXISTENTE", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Verifica tus credenciales", Toast.LENGTH_SHORT).show()
        }
    }
}