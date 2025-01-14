package com.example.android_bootcamp.helper_clases


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_bootcamp.card.ItemModel
import com.example.android_bootcamp.card.Type

class ItemViewModel : ViewModel() {

    private val _itemList = MutableLiveData<List<ItemModel>>()
    val itemList: LiveData<List<ItemModel>> get() = _itemList

    init {
        //we add default card at the beginning
        _itemList.value = listOf(
            ItemModel(
                type = Type.VISA,
                number = "2345678987654321",
                holderName = "Jane Smith",
                validThru = "11/23",
                cvv = "456"
            )
        )
    }

    fun addItem(item: ItemModel) {
        val currentList = _itemList.value ?: listOf()
        _itemList.value = currentList + item
    }

    fun removeItem(itemId: String) {
        val currentList = _itemList.value ?: listOf()
        _itemList.value = currentList.filterNot { it.id == itemId }
    }

    fun clearItems() {
        _itemList.value = listOf()
    }
}
