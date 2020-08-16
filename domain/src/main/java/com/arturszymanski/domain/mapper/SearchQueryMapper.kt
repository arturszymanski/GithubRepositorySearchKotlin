package com.arturszymanski.domain.mapper

import com.arturszymanski.domain.entity.SearchQuery

const val NAME_QUERY = "name:"

fun SearchQuery.toDTO(): String
    = NAME_QUERY + name