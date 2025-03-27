package com.example.android_bootcamp.presentation.features.main


import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android_bootcamp.presentation.common.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.presentation.features.options.OptionsBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
     private val viewModel: MainViewModel by viewModels()

     private val galleryLauncher =
          registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
               uri?.let {
                    viewModel.handleEvent(MainEvent.ImageSelected(it))
               }
          }

     private val cameraLauncher =
          registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
               if (success) {
                    viewModel.state.value.tempCameraUri?.let { uri ->
                         viewModel.handleEvent(MainEvent.ImageSelected(uri))
                    }
               }
          }

     override fun setUp() {
          setupObservers()
     }

     override fun setListeners() {
          binding.uploadId.setOnClickListener {
               showOptionsBottomSheet()
          }

          binding.uploadStorageId.setOnClickListener {
               viewModel.handleEvent(MainEvent.UploadImage)
          }
     }

     private fun setupObservers() {
          viewLifecycleOwner.lifecycleScope.launch {
               viewModel.state.collectLatest { state ->
                    binding.uploadStorageId.isEnabled = state.isButtonEnabled
                    state.bitmap?.let {
                         binding.photoId.setImageBitmap(it)
                         binding.photoId.setImageURI(null)
                    }
                    state.uri?.let {
                         binding.photoId.setImageURI(it)
                    }
                    state.error?.let {
                         Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                         viewModel.clearErrorState()
                    }
               }
          }
     }

     private fun showOptionsBottomSheet() {
          OptionsBottomSheet().apply {
               setOnImageSelectionListener { isCamera ->
                    if (isCamera) openCamera() else openGallery()
               }
          }.show(parentFragmentManager, "OptionsBottomSheet")
     }

     private fun openGallery() {
          galleryLauncher.launch("image/*")
     }

     private fun openCamera() {
          getTempFileUri().let { uri ->
               viewModel.handleEvent(MainEvent.CameraUriCreated(uri))
               cameraLauncher.launch(uri)
          }
     }

     private fun getTempFileUri(): Uri {
          val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
          val file = File.createTempFile("IMG_${System.currentTimeMillis()}", ".jpg", storageDir)
          return FileProvider.getUriForFile(
               requireContext(),
               "${requireContext().packageName}.provider",
               file
          )
     }
}
