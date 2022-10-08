package com.jaax.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.jaax.login.R
import com.jaax.login.data.login.LoginMVP
import com.jaax.login.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginMVP.View {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var presenter: LoginMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            presenter.verifySession()
            delay(4000)
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                presenter.loginButtonClicked()
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    override fun getUsername(): String {
        return binding.etUsername.text.toString()
    }

    override fun getPassword(): String {
        return binding.etPassword.text.toString()
    }

    override fun grantAccess(granted: Boolean) {
        if(granted) {
            val intent = Intent(this, ShowUsersActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.finish()
        } else {
            Toast.makeText(this, getString(R.string.invalidCredentials), Toast.LENGTH_SHORT).show()
        }
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.ocurrio_error), Toast.LENGTH_SHORT).show()
    }

    override fun stateButton() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.btnLogin.isEnabled = false
            binding.etUsername.isEnabled = false
            binding.etPassword.isEnabled = false
            delay(2500)
            binding.btnLogin.isEnabled = true
            binding.etUsername.isEnabled = true
            binding.etPassword.isEnabled = true
        }
    }

    override fun initActivity(isSessionAlive: Boolean) {
        if(isSessionAlive) {
            val intent = Intent(this, ShowUsersActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.finish()
        }
    }
}