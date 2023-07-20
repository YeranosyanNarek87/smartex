package com.example.smartex.ui.main

import androidx.lifecycle.ViewModel
import com.example.smartex.data.Date
import com.example.smartex.data.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val currentState = MutableStateFlow(Date())
    val state: StateFlow<Date>
        get() = currentState.asStateFlow()

    private var id = -1

    fun handleClick(date: String) {
        val oldList: MutableList<Item> = currentState.value.date.toMutableList()
        oldList.add(Item(date = date, id = id + 1))
        id++
        val newList = oldList.toList()
        currentState.value = Date(date = newList)
    }

    val onItemClick: (Item) -> Unit = { _ ->
        // Here can implement any logic regarding item click
    }
}
