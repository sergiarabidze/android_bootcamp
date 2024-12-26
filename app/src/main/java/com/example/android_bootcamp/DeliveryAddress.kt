package com.example.android_bootcamp

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bootcamp.databinding.FragmentDeliveryAddressBinding



class DeliveryAddress : Fragment() {
    private var _binding: FragmentDeliveryAddressBinding? = null
    private val binding get() = _binding!!//binding

    private val items: MutableList<ItemModel> = mutableListOf(
        ItemModel(1, false, "SBI Building, street 3, Software Park", "To home"),
        ItemModel(2, false, "SBI Building, street 3, Software Park", "To office", icon = R.drawable.office)
    )//list of items and first two item is already entered
    private var id = 3//id for unique items we start from 3 becuase we know previus two
    private val adapter by lazy {
        LocationListAdapter(
            onEditClick = { item -> openUpdateFragment(item) },
            onDeleteClick = { item -> deleteItem(item) }
        )
    }//location adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeliveryAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
    }

    private fun listeners() {
        initializaations()
        binding.addButtonId.setOnClickListener {
            val addLocationFragment = AddLocation()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_id, addLocationFragment)
                .addToBackStack(null)
                .commit()
        }//when we click add item we go to different fragment

        binding.backArrowId.setOnClickListener {
            requireActivity().finish()
        }//back button

        //Listen for adding new location
        parentFragmentManager.setFragmentResultListener("locationResult", viewLifecycleOwner) { _, bundle ->
            val locationInfo = bundle.getString("location_info")
            val addressInfo = bundle.getString("address_info")//we get information from the addfragment and then use it to add in the list

            if (!locationInfo.isNullOrEmpty() && !addressInfo.isNullOrEmpty()) {
                val newItem = ItemModel(id++, false, locationInfo, addressInfo)//we create new item with unique id from the information we got from the second fragment
                items.add(0, newItem)//we add it in the first index
                adapter.submitList(items.toList())//we submit the list to see changes
            }
        }

        //Listen for update result from Update fragment
        parentFragmentManager.setFragmentResultListener("updateResult", viewLifecycleOwner) { _, bundle ->
            val itemId = bundle.getInt("item_id")
            val locationInfo = bundle.getString("location_info")
            val addressInfo = bundle.getString("address_info")//same thing here we get new information for the item we want to update
            updateItem(itemId, locationInfo, addressInfo)
        }
    }

    private fun initializaations() {//
        binding.recyclerViewId.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewId.adapter = adapter
        adapter.submitList(items)
    //we just add first two already existing items in the recycler view and submit it and also add adapter to the recycler view
    }

    private fun openUpdateFragment(item: ItemModel) {
        val updateFragment = Update().apply {
            arguments = Bundle().apply {
                putInt("item_id", item.id)
            }//we send information to the update fragment
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_id, updateFragment)
            .addToBackStack(null)
            .commit()

    }

    private fun updateItem(itemId: Int, locationInfo: String?, addressInfo: String?) {
        val index = items.indexOfFirst { it.id == itemId }//we find the index of the item we want to update
        if (index != -1 && (locationInfo != null || addressInfo != null)) {
            val item = items[index]
            items[index] = ItemModel(
                id = item.id,
                ischecked = item.ischecked,
                info = locationInfo?.ifEmpty { item.info } ?: item.info,//if the user will not enter the value for the any component we keep it the same
                address = addressInfo?.ifEmpty { item.address } ?: item.address
            )
            adapter.submitList(items.toList())//after updating we still submit list
        }
    }

    private fun deleteItem(item: ItemModel) {
     items.remove(item)
        adapter.submitList(items.toList())

    //if the person clicks on the view longer we will delete item from the list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
