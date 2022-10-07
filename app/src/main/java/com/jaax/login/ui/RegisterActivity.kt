package com.jaax.login.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jaax.login.data.register.RegisterMVP
import com.jaax.login.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
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

    override fun userRegistered(registered: Boolean) {
        if(registered) {
            Toast.makeText(this, ":D", Toast.LENGTH_SHORT).show()
            this.finish()
        } else {
            Toast.makeText(this, ":/", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}