package com.example.android_bootcamp.presentation.helper

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Fragment.showToast(message: String) {
    val toast = android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT)
    toast.show()
}

fun Fragment.launchCoroutine(dispatcher : CoroutineDispatcher = Dispatchers.Main, block: suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch(dispatcher) {
        repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
            block.invoke()
        }
    }

}