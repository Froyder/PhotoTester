package com.example.phototester.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.phototester.R

class DeleteImageDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireContext().let {
            AlertDialog.Builder(it)
                .setTitle(requireContext().resources.getString(R.string.warning))
                .setMessage(requireContext().resources.getString(R.string.delete_picture))
                .setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_delete_pic, null))
                .setPositiveButton(requireContext().resources.getString(R.string.yes)){ _, _ ->
                    setFragmentResult(REQUEST_KEY, bundleOf(REQUEST to POSITIVE))
                }
                .setNegativeButton(requireContext().resources.getString(R.string.no)){ dialog, _ ->
                    dialog.dismiss()
                }
                .create()
        }
    }

    companion object {
        private const val REQUEST_KEY = "request_key"
        private const val REQUEST = "request"
        private const val POSITIVE = "yes"

        const val TAG = "DeleteImageDialogFragment"
    }
}