package com.example.android_bootcamp.api
import kotlinx.serialization.*


@Serializable
data class Field(
    @SerialName("field_id") val fieldId: Int,
    val hint: String,
    @SerialName("field_type") val fieldType: String,
    val keyboard: String = "text",
    val required: Boolean,
    @SerialName("is_active") val isActive: Boolean,
    val icon: String
)


typealias FieldList = List<List<Field>>

