package com.example.phototester.view

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.phototester.R
import com.example.phototester.databinding.CameraFragmentBinding
import com.example.phototester.viewmodel.CameraViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import timber.log.Timber

class CameraFragment : Fragment() {

    private var _binding: CameraFragmentBinding? = null
    private val binding get() = _binding!!

    private val koinScope: Scope by lazy {
        getKoin().getOrCreateScope(CAMERA_SCOPE_ID, named(CAMERA_SCOPE))
    }
    private val viewModel: CameraViewModel by koinScope.inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CameraFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get(DATA_STRING) as Bitmap
                binding.mainImageview.setImageBitmap(imageBitmap)
                binding.savePicButton.visibility = View.VISIBLE
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mutableLiveData.observe(viewLifecycleOwner) { renderData(it) }

        binding.takePicButton.setOnClickListener { onPictureButtonPushed() }

        binding.savePicButton.setOnClickListener {
            try {
                val bitmap = (binding.mainImageview.drawable as BitmapDrawable).bitmap
                viewModel.onSaveButtonPushed(bitmap)
            } catch (error: ClassCastException) {
                onErrorOccurred(error)
            }
        }
    }

    private fun onPictureButtonPushed() {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(intent)
        } catch (error: ActivityNotFoundException) {
            onErrorOccurred(error)
        }
    }

    private fun renderData(uri: Uri) {
        binding.mainImageview.setImageResource(R.drawable.ic_launcher_foreground)
        binding.savePicButton.visibility = View.INVISIBLE
        Toast.makeText(context, getString(R.string.on_picture_saved_message), Toast.LENGTH_SHORT).show()
        Timber.i("Timber talks: $uri saved successfully")
    }

    private fun onErrorOccurred(error: Throwable) {
        Timber.i("Timber talks: Oops! We-ve got some $error")
        Toast.makeText(context, getString(R.string.on_wrong_type_message), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        koinScope.close()
    }

    companion object Factory {
        private const val CAMERA_SCOPE = "camera_scope"
        private const val CAMERA_SCOPE_ID = "cameraScopeId"
        private const val DATA_STRING = "data"

        fun newInstance(): Fragment = CameraFragment()
    }
}