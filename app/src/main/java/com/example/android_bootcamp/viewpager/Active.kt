package com.example.android_bootcamp.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bootcamp.R
import com.example.android_bootcamp.databinding.FragmentActiveBinding
import com.example.android_bootcamp.recycler.ItemAdapter
import com.example.android_bootcamp.recycler.ItemModel

class Active : Fragment() {
    private var _binding:FragmentActiveBinding? = null
    private val binding get() = _binding!!

    private val itemList: List<ItemModel> = listOf(
        ItemModel(R.drawable.blackchairjpg, "cool chair", "black", 10, 100.0, "Active"),
        ItemModel(R.drawable.gaming, "cool gaming chair", "black", 5, 50.0, "Active"),
        ItemModel(R.drawable.redchair, "red chair", "red", 15, 150.0, "Active"),
        ItemModel(R.drawable.gaming, "gaming", "black", 3, 30.0, "Active")
    )//list for items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.activeRecyclerId.layoutManager = LinearLayoutManager(context)
        val adapter = ItemAdapter(itemList){}
        binding.activeRecyclerId.adapter = adapter
//recycler view for this page
    }
}