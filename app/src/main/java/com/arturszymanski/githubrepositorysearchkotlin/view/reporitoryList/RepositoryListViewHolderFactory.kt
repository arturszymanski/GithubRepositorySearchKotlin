package com.arturszymanski.githubrepositorysearchkotlin.view.reporitoryList

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arturszymanski.githubrepositorysearchkotlin.R

class RepositoryListViewHolderFactory {

    fun createViewHolder(viewGroup: ViewGroup): RepositoryListViewHolder =
        RepositoryListViewHolder(
            itemView = LayoutInflater
                .from(viewGroup.context)
                .inflate(
                    R.layout.item_reporitory_list,
                    viewGroup,
                    false
                )
        )
}