package com.example.android_bootcamp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.example.android_bootcamp.R
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val args: MainFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                    MainScreen(
                        email = args.email,
                        onLogout = {
                            viewModel.clearSession {
                                navigateToLogin()
                            }
                        },
                        onUsersClick = {
                            findNavController(this@MainFragment).navigate(R.id.usersFragment)
                        }
                    )
                }
            }
        }


    private fun navigateToLogin() {
        val navOptions = navOptions {
            popUpTo(R.id.mainFragment) { inclusive = true }
        }
        findNavController(this).navigate(R.id.login, null, navOptions)
    }
}
