package com.hrishikeshdarshan.ablertsonshrishiassignment.util

import androidx.recyclerview.widget.DiffUtil
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.WordDetail

class MyDiffUtil(
    private val oldList: List<WordDetail>,
    private val newList: List<WordDetail>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].longForm == newList[newItemPosition].longForm
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].longForm != newList[newItemPosition].longForm -> false
            oldList[oldItemPosition].frequency != newList[newItemPosition].frequency -> false
            oldList[oldItemPosition].since != newList[newItemPosition].since -> false
            else -> true
        }
    }
}