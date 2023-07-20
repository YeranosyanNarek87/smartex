package com.example.smartex.ui.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.smartex.data.Item
import com.example.smartex.databinding.ItemLayoutBinding

class MyAdapter(
    private val onItemClicked: ((Item) -> Unit)
) : ListAdapter<Item, MyViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context)), onItemClicked)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(getItem(position))

    private companion object {
        val diffCallback = object :
            DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(
                oldDate: Item,
                newDate: Item
            ): Boolean = oldDate.id == newDate.id

            override fun areContentsTheSame(
                oldDate: Item,
                newDate: Item
            ): Boolean = oldDate.id == newDate.id
        }
    }
}
