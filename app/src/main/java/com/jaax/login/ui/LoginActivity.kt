package com.jaax.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.jaax.login.data.login.LoginMVP
import com.jaax.login.databinding.ActivityLoginBinding
import com.jaax.login.util.Utils.Companion.TAG_ERROR_MESSAGE
import com.jaax.login.util.Utils.Companion.TAG_INVALID_CREDENTIALS
import com.jaax.login.util.Utils.Companion.TAG_INVALID_MESSAGE
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
            InvalidCredentials().show(supportFragmentManager, TAG_INVALID_CREDENTIALS)
        }
    }

    override fun showUnsuccessfulMessage() {
        UnsuccessfulMessage().show(supportFragmentManager, TAG_INVALID_MESSAGE)
    }

    override fun showError() {
        ErrorMessage().show(supportFragmentManager, TAG_ERROR_MESSAGE)
    }

    override fun stateButton() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.btnLogin.isEnabled = false
            binding.etUsername.isEnabled = false
            binding.etPassword.isEnabled = false
            binding.tvRegister.isEnabled = false
            delay(2500)
            binding.btnLogin.isEnabled = true
            binding.etUsername.isEnabled = true
            binding.etPassword.isEnabled = true
            binding.tvRegister.isEnabled = true
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