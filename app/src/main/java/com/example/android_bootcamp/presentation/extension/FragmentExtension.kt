package com.example.android_bootcamp.presentation.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


fun Fragment.launchCoroutine(block: suspend () -> Unit){
    viewLifecycleOwner.lifecycleScope.launch{
        repeatOnLifecycle(Lifecycle.State.STARTED){
            block.invoke()
        }
    }
}

fun Fragment.showSnackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
}