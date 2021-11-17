package com.example.phototester.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class DeleteDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireContext().let {
            AlertDialog.Builder(it)
                .setTitle("Warning!")
                .setMessage("Do you really want to delete all photos?")
                .setPositiveButton("Yes"){dialog, button ->
                    setFragmentResult(REQUEST_KEY, bundleOf(REQUEST to POSITIVE))
                }
                .setNegativeButton("No"){dialog, button ->
                    dialog.dismiss()
                }
                .create()
        }
    }

    companion object {
        private const val REQUEST_KEY = "request_key"
        private const val REQUEST = "request"
        private const val POSITIVE = "yes"

        const val TAG = "DeleteDialogFragment"
    }
}