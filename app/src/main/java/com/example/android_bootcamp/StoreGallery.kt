package com.example.android_bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bootcamp.databinding.FragmentStoreGalleryBinding

class StoreGallery : Fragment() {
    private var _binding: FragmentStoreGalleryBinding? = null
    private val binding get() = _binding!!

    //assigning binding variables
    private val categories: MutableList<CategoryModel> =
        mutableListOf()//list for storing categories
    private val items: MutableList<ItemModel> = mutableListOf()//list for storing items/products
    private lateinit var itemAdapter: ItemAdapter
    //we need to define this later,we created it globally cause im using it in the filter function.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assignments()
        adapters()
        listeners()

    }

    private fun adapters() {

        val categoryRecyclerView = binding.categoryRecyclerView//binding the recycler view
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )//setting the layout manager to horizontal

        val categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            filterItems(selectedCategory)//we define the function that will take category as an input and then do the filtering
        }
        categoryRecyclerView.adapter = categoryAdapter//setting the adapter

        val itemRecyclerView = binding.itemsRecyclerView//binding the recycler view
        itemRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2
        )//we use gridlayout and set the number of rows to 2
        itemAdapter =
            ItemAdapter(items)//here is previously defined adapter and we are assigning to the itemAdapter
        itemRecyclerView.adapter = itemAdapter

        filterItems(categories[0])//we need to filter this to show all the items initially when the program launches it automatically should show all the items


    }


    private fun filterItems(selectedCategory: CategoryModel) {
        val filteredItems =
            if (selectedCategory.name == "All") {//if the category name is all we just show every item
                items
            } else {
                items.filter { it.type == selectedCategory }
            }
        itemAdapter.updateItems(filteredItems)//here we update the items in the adapter and that's why we are using globally defined itemAdapter
    }

    private fun assignments() {
        //just assignments of the lists for categories and items
        categories.apply {
            add(CategoryModel(getString(R.string.all)))
            add(CategoryModel(getString(R.string.party)))
            add(CategoryModel(getString(R.string.campaign)))
            add(CategoryModel(getString(R.string.category_1)))
            add(CategoryModel(getString(R.string.category_2)))
            add(CategoryModel(getString(R.string.category_3)))
        }
        items.apply {
            add(
                ItemModel(
                    R.drawable.blackwoman,
                    getString(R.string.belt_suit_blazer),
                    getString(R.string.price),
                    CategoryModel(getString(R.string.party))
                )
            )
            add(
                ItemModel(
                    R.drawable.womanone,
                    getString(R.string.belt_suit_blazer),
                    getString(R.string.price),
                    CategoryModel(getString(R.string.campaign))
                )
            )
            add(
                ItemModel(
                    R.drawable.womantwo,
                    getString(R.string.belt_suit_blazer),
                    getString(R.string.price),
                    CategoryModel(getString(R.string.category_1))
                )
            )
            add(
                ItemModel(
                    R.drawable.woman3,
                    getString(R.string.belt_suit_blazer),
                    getString(R.string.price),
                    CategoryModel(getString(R.string.category_2))
                )
            )
            add(
                ItemModel(
                    R.drawable.blackwoman,
                    getString(R.string.belt_suit_blazer),
                    getString(R.string.price),
                    CategoryModel(getString(R.string.party))
                )
            )
            add(
                ItemModel(
                    R.drawable.womanone,
                    getString(R.string.belt_suit_blazer),
                    getString(R.string.price),
                    CategoryModel(getString(R.string.campaign))
                )
            )
            add(
                ItemModel(
                    R.drawable.womantwo,
                    getString(R.string.belt_suit_blazer),
                    getString(R.string.price),
                    CategoryModel(getString(R.string.category_2))
                )
            )
            add(
                ItemModel(
                    R.drawable.woman3,
                    getString(R.string.belt_suit_blazer),
                    getString(R.string.price),
                    CategoryModel(getString(R.string.category_2))
                )
            )
        }
    }

    private fun listeners() {
        with(binding) {
            homeBtnId.setOnClickListener {
                Toast.makeText(requireContext(), "home button clicked!", Toast.LENGTH_SHORT).show()
            }
            heartBtnId.setOnClickListener {
                Toast.makeText(requireContext(), "heart button clicked!", Toast.LENGTH_SHORT).show()
            }
            textBtnId.setOnClickListener {
                Toast.makeText(requireContext(), "message button clicked!", Toast.LENGTH_SHORT)
                    .show()
            }
            bellBtnId.setOnClickListener {
                Toast.makeText(requireContext(), "bell button clicked!", Toast.LENGTH_SHORT).show()
            }
            starBtnId.setOnClickListener {
                Toast.makeText(requireContext(), "star button clicked!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null//to avoid memory leaks we should set binding to null on onDestroyView
    }


}