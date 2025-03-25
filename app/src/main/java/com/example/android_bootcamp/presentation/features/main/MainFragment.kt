package com.example.android_bootcamp.presentation.features.main


import android.net.Uri
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.example.android_bootcamp.presentation.common.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.presentation.features.options.OptionsBottomSheet
import java.io.File

class MainFragment : BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
     private val viewModel: MainViewModel by viewModels()

     private var selectedImageUri: Uri? = null

     private val galleryLauncher =
          registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
               uri?.let { handleImageSelection(it) }
          }

     private val cameraLauncher =
          registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
               if (success) selectedImageUri?.let { handleImageSelection(it) }
          }

     override fun setUp() {
          setupClickListeners()
          setupObservers()
     }

     private fun setupClickListeners() {
          binding.uploadId.setOnClickListener {
               showOptionsBottomSheet()
          }
     }

     private fun setupObservers() {
          viewModel.compressedBitmap.observe(viewLifecycleOwner) { bitmap ->
               bitmap?.let {
                    binding.photoId.setImageBitmap(it)
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
               selectedImageUri = uri
               cameraLauncher.launch(uri)
          }
     }

     private fun handleImageSelection(uri: Uri) {
          viewModel.compressImage(requireContext(), uri)
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
