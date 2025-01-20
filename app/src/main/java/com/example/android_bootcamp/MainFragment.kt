package com.example.android_bootcamp


import androidx.lifecycle.ViewModelProvider
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding


class MainFragment : BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
    private lateinit var chatViewModel: ChatViewModel
    private val adapter by lazy { ChatAdapter() }
    override fun setUp() {
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        val chats = chatViewModel.getChats()

        binding.recyclerId.adapter = adapter
        adapter.submitList(chats)
    }

    override fun setListeners() {
        super.setListeners()
        binding.searchButton.setOnClickListener {
            val name = binding.searchBar.text.toString()
            val filteredChats = chatViewModel.getChats().filter { chat ->
                chat.owner.contains(name)
            }
            adapter.submitList(filteredChats)
        }
    }
}

