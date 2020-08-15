package com.arturszymanski.githubrepositorysearchkotlin.view.reporitoryList

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arturszymanski.domain.entity.Repository
import javax.inject.Inject

class RepositoryListAdapter @Inject constructor(
    private val diffCallback: RepositoryListDiffCallback,
    private val viewHolderFactory: RepositoryListViewHolderFactory
) : RecyclerView.Adapter<RepositoryListViewHolder>() {

    var itemInteractions: RepositoryListItemInteractions? = null
    var data: List<Repository> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder =
        viewHolderFactory
            .createViewHolder(parent)

    override fun getItemCount(): Int =
        data.size

    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        holder
            .bind(
                repository = data[position],
                repositoryListItemInteractions = itemInteractions
            )
    }

    fun updateData(data: List<Repository>) {
        this.data = data
        diffCallback
            .calculate(
                oldData = this.data,
                newData = data
            )
            .dispatchUpdatesTo(this)
    }
}