package com.arturszymanski.domain.mapper

import com.arturszymanski.data.entity.PageDTO
import com.arturszymanski.domain.entity.Page
import javax.inject.Inject

class PageMapper @Inject constructor() {

    fun <InputItemType, OutputItemType> map(
        pageDTO: PageDTO<InputItemType>,
        innerMapper: (InputItemType) -> OutputItemType ) : Page<OutputItemType> {

        val data: List<OutputItemType> = pageDTO.items
            .map { innerMapper.invoke(it) }

        return Page(
            items = data,
            totalCount = pageDTO.totalCount
        )
    }
}