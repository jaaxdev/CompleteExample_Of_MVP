package com.jaax.login.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jaax.login.R
import com.jaax.login.data.register.RegisterMVP
import com.jaax.login.databinding.ActivityRegisterBinding
import com.jaax.login.util.Utils
import com.jaax.login.util.Utils.Companion.TAG_INVALID_MESSAGE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity: AppCompatActivity(), RegisterMVP.View {
    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var presenter: RegisterMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.btnSaveUser.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){ presenter.registerButtonClicked() }
        }
    }

    override fun getUsername(): String {
        return binding.etUsername.text.toString()
    }

    override fun getPassword(): String {
        return binding.etPassword.text.toString()
    }

    override fun showUnsuccessfulMessage() {
        UnsuccessfulMessage().show(supportFragmentManager, TAG_INVALID_MESSAGE)
    }

    override fun showError() {
        ErrorMessage().show(supportFragmentManager, Utils.TAG_ERROR_MESSAGE)
    }

    private fun stateButton() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.btnSaveUser.isEnabled = false
            binding.etUsername.isEnabled = false
            binding.etPassword.isEnabled = false
            delay(2000)
            binding.btnSaveUser.isEnabled = true
            binding.etUsername.isEnabled = true
            binding.etPassword.isEnabled = true
        }
    }

    override fun userRegistered(registered: Boolean) {
            if(registered) {
                Toast.makeText(this@RegisterActivity, getString(R.string.successful_register), Toast.LENGTH_SHORT).show()
                stateButton()
                this@RegisterActivity.finish()
            } else {
                UnsuccessfulMessage().show(supportFragmentManager, TAG_INVALID_MESSAGE)
                stateButton()
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}