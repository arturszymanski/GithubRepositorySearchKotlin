package com.arturszymanski.domain.mapper

import com.arturszymanski.data.entity.OwnerDTO
import com.arturszymanski.domain.entity.Owner

fun OwnerDTO.toDomain(): Owner =
    Owner(
        id = id,
        login = login
    )