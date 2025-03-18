package com.example.android_bootcamp.presentation.features.main


import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bootcamp.presentation.common.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.presentation.extension.launchCoroutine
import com.example.android_bootcamp.presentation.extension.showSnackBar
import com.example.android_bootcamp.presentation.features.helper.ExcavatorAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
     private val viewModel: MainViewModel by viewModels()
     private lateinit var adapter: ExcavatorAdapter


     override fun setUp() {
          super.setUp()

          binding.etSearch.doAfterTextChanged { text ->
               if (text != null) {
                    if (text.length>2) {
                         viewModel.onEvent(ExcavatorEvent.Search(text.toString()))
                    }else if(text.isEmpty()){
                         viewModel.onEvent(ExcavatorEvent.FetchExcavators)
                    }
               }
          }

          launchCoroutine{
               viewModel.state.collect { state ->
                    adapter.submitList(state.excavators.toList())
                    binding.recyclerView.isVisible = !state.isLoading
                    binding.progressId.isVisible=state.isLoading
                    if (state.errorMessage!=null){
                         showSnackBar(state.errorMessage)
                         viewModel.clearErrorMessage()
                    }
               }
          }
     }

     override fun setRecycler() {
          adapter = ExcavatorAdapter()
          binding.recyclerView.adapter = adapter
          binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

     }

}
