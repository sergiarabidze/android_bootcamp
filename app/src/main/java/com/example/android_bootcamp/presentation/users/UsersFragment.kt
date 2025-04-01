package com.example.android_bootcamp.presentation.users




import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.databinding.FragmentUsersBinding
import com.example.android_bootcamp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                UsersScreen(
                    viewModel = viewModel,
                    onBackClick = { findNavController().popBackStack() }
                )
            }
        }
    }
}
