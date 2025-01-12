package com.example.android_bootcamp.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bootcamp.R
import com.example.android_bootcamp.Rating
import com.example.android_bootcamp.databinding.FragmentCompletedBinding
import com.example.android_bootcamp.recycler.ItemAdapter
import com.example.android_bootcamp.recycler.ItemModel

class Completed : Fragment() {
    private var _binding: FragmentCompletedBinding? = null
    private val binding get() = _binding!!

    private val itemList: List<ItemModel> = listOf(
        ItemModel(R.drawable.redchair, "red chair", "red", 10, 100.0, "Completed"),
        ItemModel(R.drawable.royal, "chair for king", "red", 5, 50.0, "Completed"),
        ItemModel(R.drawable.blackchairjpg, "very cool chair", "black", 15, 150.0, "Completed"),
        ItemModel(R.drawable.royal, "chair for queen", "red", 3, 30.0, "Completed")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.completedRecyclerId.layoutManager = LinearLayoutManager(context)

        val adapter = ItemAdapter(itemList) { item ->
            //when an item is clicked pass the ItemModel object to the Rating Bottom Sheet
            val ratingBottomSheet = Rating()
            val bundle = Bundle()
            bundle.putParcelable("item_model", item) //pass the ItemModel object
            ratingBottomSheet.arguments = bundle
            ratingBottomSheet.show(parentFragmentManager, ratingBottomSheet.tag)//show the bottom sheet
        }
        binding.completedRecyclerId.adapter = adapter

    }
}
