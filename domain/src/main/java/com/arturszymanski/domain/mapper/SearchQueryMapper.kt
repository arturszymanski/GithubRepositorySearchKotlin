package com.arturszymanski.domain.mapper

import com.arturszymanski.domain.entity.SearchQuery

const val NAME_QUERY = " in:name"
/**
 * Github do not accepts search query without params, even if they have such (not working) samples
 * in documentation, so small hack :)
 */
const val DEFAULT = "forks:<=0"

fun SearchQuery.toDTO(): String =
    if(name.isEmpty()) {
        DEFAULT
    }
    else {
        name + NAME_QUERY
    }