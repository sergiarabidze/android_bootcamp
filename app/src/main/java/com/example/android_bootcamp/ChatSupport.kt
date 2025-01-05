package com.example.android_bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bootcamp.databinding.FragmentChatSupportBinding
import java.util.Calendar

class ChatSupport : Fragment() {
    private var _binding: FragmentChatSupportBinding? = null
    private val binding get() = _binding!!//initializing binding
    private val adapter: ChatterAdapter by lazy { ChatterAdapter() }//adapter for recycler
    private val chat:MutableList<Chatter> = mutableListOf()//list for messages

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatSupportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter()
        listeners()//listeners for buttons

    }

    private fun adapter(){
        val recycler = binding.chatRecycler
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }

    private fun listeners(){
        binding.backArrow.setOnClickListener {
            requireActivity().finish()
        }

        binding.sendButton.setOnClickListener{
            val text = binding.editText.text.toString()
            val calendar = Calendar.getInstance()//calendar for real time its java class
            val hours = calendar.get(Calendar.HOUR_OF_DAY)
            val minutes = calendar.get(Calendar.MINUTE)
            if(chat.size%2==0){//logic for admin and normal user
                chat.add(0,Chatter(text = text,time = "Today,$hours:$minutes", type = UserType.NORMAL))//for every user we initialize new message
            }else{
                chat.add(0,Chatter(text = text, time = "Today,$hours:$minutes", type = UserType.ADMIN))
            }
            adapter.submitList(chat.toList())
            binding.editText.setText("")//clear text after send
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null//avoid memory leaks
    }

}