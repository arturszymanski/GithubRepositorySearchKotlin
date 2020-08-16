package com.arturszymanski.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
    val id: Long,
    val name: String,
    val owner: Owner,
    val isFork: Boolean,
    val isPrivate: Boolean,
    val forksCount: Long,
    val watchersCount: Long,
    val openIssuesCount: Long
) : Parcelable