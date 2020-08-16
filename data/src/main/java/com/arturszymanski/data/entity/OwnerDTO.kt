package com.arturszymanski.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerDTO(
    val id: Long,
    val login: String
)