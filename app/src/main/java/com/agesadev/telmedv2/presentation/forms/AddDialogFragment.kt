package com.agesadev.telmedv2.presentation.forms

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.agesadev.telmedv2.R
import com.google.android.material.textfield.TextInputEditText


class AddDialogFragment : DialogFragment() {

    interface DialogListener {
        fun onDialogPositiveClick(text: String)
    }

    private lateinit var listener: DialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as DialogListener

        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement DialogListener")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_add_dialog, null)
        val editText = view.findViewById<TextInputEditText>(R.id.addInputEditText)

        builder.setView(view)
            .setTitle("Enter text")
            .setPositiveButton("Save") { _, _ ->
                val text = editText.text.toString()
                listener.onDialogPositiveClick(text)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }
}