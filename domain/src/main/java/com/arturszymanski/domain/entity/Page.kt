package com.arturszymanski.domain.entity

data class Page<PagedItem> (
    val items: List<PagedItem>,
    val totalCount: Int
)