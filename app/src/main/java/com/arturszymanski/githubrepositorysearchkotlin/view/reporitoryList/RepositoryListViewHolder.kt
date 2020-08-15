package com.arturszymanski.githubrepositorysearchkotlin.view.reporitoryList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.githubrepositorysearchkotlin.R
import kotlinx.android.synthetic.main.item_reporitory_list.view.*

class RepositoryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        repository: Repository,
        repositoryListItemInteractions: RepositoryListItemInteractions?
    ) =
        with(itemView) {
            setOnClickListener {
                repositoryListItemInteractions?.itemSelected(adapterPosition, repository)
            }

            nameLabel.text = repository.name
            ownerLabel.text = repository.owner.login

            if (repository.isFork) {
                isForkImage.setImageResource(R.drawable.vector_icon_split)
                isForkLabel.text = context.getString(R.string.repository_fork)
            } else {
                isForkImage.setImageResource(R.drawable.vector_icon_merge)
                isForkLabel.text = context.getString(R.string.repository_main)
            }

            if (repository.isPrivate) {
                isPrivateImage.setImageResource(R.drawable.vector_icon_lock_open)
                isPrivateLabel.text = context.getString(R.string.repository_private)
            } else {
                isPrivateImage.setImageResource(R.drawable.vector_icon_lock_open)
                isPrivateLabel.text = context.getString(R.string.repository_public)
            }
        }
}