package com.arturszymanski.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val id: Long,
    val login: String
) : Parcelable