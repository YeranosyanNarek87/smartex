package com.example.smartex.ui.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.smartex.data.BaseItem

abstract class BaseViewHolder<BID : BaseItem>(
    view: View,
    private val onItemClicked: ((BID) -> Unit)
) : RecyclerView.ViewHolder(view) {

    open fun bind(itemData: BID) {
        itemView.setOnClickListener { onItemClicked.invoke(itemData) }
    }
}
