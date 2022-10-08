package com.jaax.login.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.jaax.login.R

class InvalidCredentials: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog
            .Builder(requireContext())
            .setMessage(getString(R.string.invalidCredentials))
            .setPositiveButton(getString(R.string.accept)) { _, _ -> dismiss() }
            .create()

        return builder
    }
}

class UnsuccessfulMessage: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog
            .Builder(requireContext())
            .setMessage(getString(R.string.unsuccessful))
            .setPositiveButton(getString(R.string.accept)) { _, _ -> dismiss() }
            .create()

        return builder
    }
}

class ErrorMessage: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog
            .Builder(requireContext())
            .setMessage(getString(R.string.error))
            .setPositiveButton(getString(R.string.accept)) { _, _ -> dismiss() }
            .create()

        return builder
    }
}