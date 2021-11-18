package com.example.phototester.di

import com.example.phototester.dataprovider.FileManager
import com.example.phototester.dataprovider.FileManagerInterface
import com.example.phototester.dataprovider.MainInteractor
import com.example.phototester.dataprovider.MainInteractorInterface
import com.example.phototester.viewmodel.CameraViewModel
import com.example.phototester.viewmodel.GalleryViewModel
import com.example.phototester.viewmodel.PhotoDetailsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val CAMERA_SCOPE = "camera_scope"
private const val GALLERY_SCOPE = "gallery_scope"
private const val DETAILS_SCOPE = "details_scope"

val application = module {
    single<FileManagerInterface> { FileManager(androidContext()) }
}

val fragments = module {
    scope(named(CAMERA_SCOPE)) {
        scoped<MainInteractorInterface> {
            MainInteractor(fileManager = get())
        }
        viewModel { CameraViewModel(get()) }
    }

    scope(named(GALLERY_SCOPE)) {
        scoped<MainInteractorInterface> {
            MainInteractor(fileManager = get())
        }
        viewModel { GalleryViewModel(get()) }
    }

    scope(named(DETAILS_SCOPE)) {
        scoped<MainInteractorInterface> {
            MainInteractor(fileManager = get())
        }
        viewModel { PhotoDetailsViewModel(get()) }
    }
}