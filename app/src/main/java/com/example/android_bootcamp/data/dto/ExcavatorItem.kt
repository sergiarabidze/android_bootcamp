package com.example.android_bootcamp.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ExcavatorItem(
    @SerializedName("bgl_number")
    val bglNumber: Int?,
    @SerializedName("bgl_variant")
    val bglVariant: Int?,
    val children: List<ExcavatorItem>,
    val createdAt: String,
    val id: String,
    val main: ExcavatorItem?,
    val name: String,
    @SerializedName("name_de")
    val nameDe: String,
    @SerializedName("order_id")
    val orderId: Int
)
