package com.arturszymanski.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageDTO<PagedItem> (
    val items: List<PagedItem>,
    @field:Json(name = "total_count")
    val totalCount: Int
)