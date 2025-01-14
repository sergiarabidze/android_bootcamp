package com.example.android_bootcamp.fragments

import com.example.android_bootcamp.card.ItemAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.helper_clases.FragmentBase
import com.example.android_bootcamp.helper_clases.ItemViewModel
import com.example.android_bootcamp.card.ItemModel
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding

class MainFragment : FragmentBase<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {

    private val itemViewModel: ItemViewModel by activityViewModels()//shared viewmodel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapters()//adapters
        listeners()

    }

    private fun listeners() {
        binding.addNewId.setOnClickListener {
            val action =
                MainFragmentDirections.actionPaymentToAddCard() // Navigates to the AddCard fragment
            findNavController().navigate(action)
        }//when add card is clicked we should go to another fragment
    }

    private fun adapters() {
        itemViewModel.itemList.observe(viewLifecycleOwner) { itemList ->
            val itemAdapter = ItemAdapter { item ->
                showDeleteConfirmationBottomSheet(item)
            }//when card is clicked ro a while we ask user if he want to remove card
            binding.viewpagerId.adapter = itemAdapter
            itemAdapter.submitList(itemList)//assigning adapter
        }
    }

    private fun showDeleteConfirmationBottomSheet(item: ItemModel) {
        val bottomSheet = DeleteConfirmationBottomSheet {
            itemViewModel.removeItem(item.id)
        }//deleting item if user clicks yes
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)

    }
}
