package com.example.phototester.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.phototester.R
import com.example.phototester.databinding.PhotoFragmentBinding

class PhotoDetailsFragment : Fragment() {

    private var _binding: PhotoFragmentBinding? = null
    private val binding get() = _binding!!

    private val picturePath: String by lazy { arguments?.getString(ARG_URI).orEmpty() }
    private val pictureDate: String by lazy { arguments?.getString(ARG_DATE).orEmpty() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhotoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.photoImageView.setImageURI(Uri.parse(picturePath))
        binding.photoTextView.text = pictureDate
    }

    companion object {
        private const val ARG_URI = "arg_uri"
        private const val ARG_DATE = "arg_date"

        fun newInstance(picturePath: String, pictureDate: String): Fragment {
            val fragment = PhotoDetailsFragment()
            fragment.arguments = bundleOf(ARG_URI to picturePath, ARG_DATE to pictureDate)
            return fragment
        }
    }
}