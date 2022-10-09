package com.jaax.login.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.jaax.login.R

class InvalidCredentials: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_layout, null)
        val tv = view.findViewById<TextView>(R.id.tvDialog)
        tv.text = getString(R.string.invalidCredentials)

        return AlertDialog
            .Builder(requireContext(), R.style.DialogStyle)
            .setView(view)
            .setPositiveButton(getString(R.string.accept)) { _, _ -> dismiss() }
            .create()
    }
}



class UnsuccessfulMessage: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_layout, null)
        val tv = view.findViewById<TextView>(R.id.tvDialog)
        tv.text = getString(R.string.unsuccessful)

        return AlertDialog
            .Builder(requireContext(), R.style.DialogStyle)
            .setView(view)
            .setPositiveButton(getString(R.string.accept)) { _, _ -> dismiss() }
            .create()
    }
}

class ErrorMessage: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_layout, null)
        val tv = view.findViewById<TextView>(R.id.tvDialog)
        tv.text = getString(R.string.error)

        return AlertDialog
            .Builder(requireContext(), R.style.DialogStyle)
            .setView(view)
            .setPositiveButton(getString(R.string.accept)) { _, _ -> dismiss() }
            .create()
    }
}