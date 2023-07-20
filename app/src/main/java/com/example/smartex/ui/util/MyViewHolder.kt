package com.example.smartex.ui.util

import com.example.smartex.data.Item
import com.example.smartex.databinding.ItemLayoutBinding

class MyViewHolder(
    private val itemBinding: ItemLayoutBinding,
    onItemClicked: ((Item) -> Unit)
) : BaseViewHolder<Item>(itemBinding.root, onItemClicked) {

    override fun bind(itemData: Item) {
        itemBinding.run {
            name.text = itemData.date
        }
        super.bind(itemData)
    }
}
