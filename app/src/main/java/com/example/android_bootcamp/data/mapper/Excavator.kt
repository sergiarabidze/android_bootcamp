package com.example.android_bootcamp.data.mapper

import com.example.android_bootcamp.data.dto.ExcavatorDto
import com.example.android_bootcamp.data.dto.ExcavatorItem
import com.example.android_bootcamp.domain.model.ExcavatorDomain

fun ExcavatorDto.toDomain(): List<ExcavatorDomain> {
    return this.flatMap { it.toFlatDomainList() }
}

fun ExcavatorItem.toFlatDomainList(depth: Int = 0): List<ExcavatorDomain> {
    val currentDepth = depth.coerceAtMost(4)
    val list = mutableListOf(
        ExcavatorDomain(
            id = this.id,
            name = this.name,
            depth = currentDepth
        )
    )
    children.forEach { child ->
        list.addAll(child.toFlatDomainList(depth + 1))
    }
    return list
}

