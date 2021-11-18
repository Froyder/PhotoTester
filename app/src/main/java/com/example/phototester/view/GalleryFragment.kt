package com.example.phototester.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.phototester.R
import com.example.phototester.databinding.GalleryFragmentBinding
import com.example.phototester.viewmodel.GalleryViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import java.io.File

class GalleryFragment : Fragment() {

    private var _binding: GalleryFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: GalleryAdapter? = null

    private val koinScope: Scope by lazy {
        getKoin().getOrCreateScope(GALLERY_SCOPE_ID, named(GALLERY_SCOPE))
    }
    private val viewModel: GalleryViewModel by koinScope.inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GalleryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setGallery()

        viewModel.mutableLiveData.observe(viewLifecycleOwner) { setAdapter(it) }

        binding.deleteButton.setOnClickListener {
            DeleteAllDialog().show(parentFragmentManager, DeleteAllDialog.TAG)
        }

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val request = bundle.getString(REQUEST).toString()
            if (request == POSITIVE) viewModel.deleteAllData()
        }
    }

    private val onListClickListener: GalleryAdapter.OnListItemClickListener =
        object : GalleryAdapter.OnListItemClickListener {
            override fun onItemClick(file: File) {
                parentFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container,
                        PhotoDetailsFragment.newInstance(file.absolutePath, file.name)
                    )
                    .addToBackStack(DEFAULT_FRAGMENT_ID)
                    .commit()
            }
        }

    private fun setAdapter(listOfFiles: Array<out File>) {
        if (adapter == null) {
            binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
            binding.recyclerView.adapter = GalleryAdapter(onListClickListener, listOfFiles)
        } else {
            adapter!!.setData(listOfFiles)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        koinScope.close()
    }

    companion object Factory {
        private const val GALLERY_SCOPE = "gallery_scope"
        private const val GALLERY_SCOPE_ID = "galleryScopeId"
        private const val DEFAULT_FRAGMENT_ID = ""
        private const val REQUEST_KEY = "request_key"
        private const val REQUEST = "request"
        private const val POSITIVE = "yes"

        fun newInstance(): Fragment = GalleryFragment()
    }
}