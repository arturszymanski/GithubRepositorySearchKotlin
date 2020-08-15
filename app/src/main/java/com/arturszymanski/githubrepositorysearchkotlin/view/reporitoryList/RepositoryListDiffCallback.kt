package com.arturszymanski.githubrepositorysearchkotlin.view.reporitoryList

import androidx.recyclerview.widget.DiffUtil
import com.arturszymanski.domain.entity.Repository
import javax.inject.Inject

class RepositoryListDiffCallback @Inject constructor() : DiffUtil.Callback() {

    lateinit var oldData: List<Repository>
    lateinit var newData: List<Repository>

    override fun getOldListSize(): Int =
        oldData.size

    override fun getNewListSize(): Int =
        newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition].id == newData[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition] == newData[newItemPosition]

    fun calculate(
        oldData: List<Repository>,
        newData: List<Repository>
    ): DiffUtil.DiffResult {
        this.oldData = oldData
        this.newData = newData

        return DiffUtil.calculateDiff(this)
    }

}