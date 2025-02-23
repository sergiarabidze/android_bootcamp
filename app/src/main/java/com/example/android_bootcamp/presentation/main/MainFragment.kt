package com.example.android_bootcamp.presentation.main


import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bootcamp.common.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.helper.resource.Resource
import com.example.android_bootcamp.helper.recycler.PostAdapter
import com.example.android_bootcamp.helper.recycler.StoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
     private val viewModel:MainViewModel by viewModels()
     private val storyAdapter by lazy { StoryAdapter() }
     private val postAdapter by lazy { PostAdapter() }

     override fun setUp() {
          super.setUp()
          observeStory()
     }

     override fun setRecycler() {
          binding.appbarLayout.setExpanded(false, false)
          binding.storyRecycler.adapter = storyAdapter
          binding.storyRecycler.layoutManager = LinearLayoutManager(
               requireContext(),
               LinearLayoutManager.HORIZONTAL,
               false
          )

          binding.postsRecycler.adapter = postAdapter
          binding.postsRecycler.layoutManager = LinearLayoutManager(
               requireContext(),
               LinearLayoutManager.VERTICAL,
               false
          )

          binding.postsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
               override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
                    val firstCompletelyVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()

                    if (firstCompletelyVisiblePosition == 0) {
                         binding.appbarLayout.setExpanded(true, true)
                    } else {
                         binding.appbarLayout.setExpanded(false, true)
                    }
               }
          })

     }



     private fun observeStory() {
          viewLifecycleOwner.lifecycleScope.launch {
               viewModel.storiesState.collectLatest { resource ->
                    when (resource){
                         is Resource.Error -> Toast.makeText(requireContext(),
                              resource.message,Toast.LENGTH_SHORT).show()

                         is  Resource.Loading -> {
                              Toast.makeText(requireContext(),"Loading",Toast.LENGTH_SHORT).show()
                         }
                         is Resource.Success -> {
                              storyAdapter.submitList(resource.data)
                         }
                         is Resource.Idle ->{
                              //ignore
                         }
                    }
               }
          }

          viewLifecycleOwner.lifecycleScope.launch {
               viewModel.postsState.collectLatest { resource ->
                    when(resource){
                         is Resource.Error ->{
                              Toast.makeText(requireContext(),
                                   resource.message,Toast.LENGTH_SHORT).show()
                         }
                         Resource.Idle ->{
                              //ignore
                         }
                         Resource.Loading ->{
                              Toast.makeText(requireContext(),"Loading",Toast.LENGTH_SHORT).show()
                         }
                         is Resource.Success ->{
                              postAdapter.submitList(resource.data)
                         }
                    }
               }
          }
     }
}