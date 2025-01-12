package com.example.android_bootcamp.recycler

import android.os.Parcel
import android.os.Parcelable
//item model for recycler and its properties
data class ItemModel(
    val imageResId: Int,
    val name: String,
    val color: String,
    val quantity: Int,
    val price: Double,
    val status: String
) :Parcelable {//its parcelable cause we are sending it to the bottom sheet
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageResId)
        parcel.writeString(name)
        parcel.writeString(color)
        parcel.writeInt(quantity)
        parcel.writeDouble(price)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemModel> {
        override fun createFromParcel(parcel: Parcel): ItemModel {
            return ItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemModel?> {
            return arrayOfNulls(size)
        }
    }
}