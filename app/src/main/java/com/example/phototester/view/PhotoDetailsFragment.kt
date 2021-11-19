package com.example.phototester.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.phototester.databinding.PhotoFragmentBinding
import com.example.phototester.viewmodel.PhotoDetailsViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class PhotoDetailsFragment : Fragment() {

    private var _binding: PhotoFragmentBinding? = null
    private val binding get() = _binding!!

    private val koinScope: Scope by lazy {
        getKoin().getOrCreateScope(DETAILS_SCOPE_ID, named(DETAILS_SCOPE))
    }
    private val viewModel: PhotoDetailsViewModel by koinScope.inject()

    private val picturePath: String by lazy { arguments?.getString(ARG_URI).orEmpty() }
    private val pictureName: String by lazy { arguments?.getString(ARG_NAME).orEmpty() }

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
        binding.photoName.text = pictureName

        viewModel.mutableLiveData.observe(viewLifecycleOwner) { renderData(it) }

        binding.deletePhotoButton.setOnClickListener {
            DeleteImageDialog().show(parentFragmentManager, DeleteImageDialog.TAG)
        }

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val request = bundle.getString(REQUEST).toString()
            if (request == POSITIVE) viewModel.onDeletePictureButtonPushed(pictureName)
        }
    }

    private fun renderData(boolean: Boolean) {
        if (!boolean) parentFragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        koinScope.close()
    }

    companion object {
        private const val DETAILS_SCOPE = "details_scope"
        private const val DETAILS_SCOPE_ID = "detailsScopeId"
        private const val REQUEST_KEY = "request_key"
        private const val REQUEST = "request"
        private const val POSITIVE = "yes"
        private const val ARG_URI = "arg_uri"
        private const val ARG_NAME = "arg_name"

        fun newInstance(picturePath: String, pictureDate: String): Fragment {
            val fragment = PhotoDetailsFragment()
            fragment.arguments = bundleOf(ARG_URI to picturePath, ARG_NAME to pictureDate)
            return fragment
        }
    }
}