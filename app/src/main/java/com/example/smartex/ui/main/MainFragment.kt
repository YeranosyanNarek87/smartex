package com.example.smartex.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.smartex.MyServiceOne
import com.example.smartex.data.Item
import com.example.smartex.databinding.FragmentMainBinding
import com.example.smartex.ui.util.MyAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: FragmentMainBinding
    private val adapter: MyAdapter by lazy { MyAdapter(viewModel.onItemClick) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listItem.adapter = adapter
        var dX = 0F
        var dY = 0F

        binding.startService.setOnClickListener { _ ->
            context?.let {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(4000)
                    MyServiceOne.startService(it, "Click for return the application")
                }
            }
        }

        binding.fab.run {
            setOnClickListener {
                viewModel.handleClick(getCurrentDateTime())
            }

            setOnTouchListener { view, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        dX = view.x - event.rawX
                        dY = view.y - event.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val newX = event.rawX + dX
                        val newY = event.rawY + dY
                        val parentView = view.parent as ViewGroup
                        val maxX = parentView.width - view.width
                        val maxY = parentView.height - view.height

                        view.x = newX.coerceIn(0F, maxX.toFloat())
                        view.y = newY.coerceIn(0F, maxY.toFloat())
                    }
                }
                false
            }
        }

        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                renderList(it.date)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderList(data: List<Item>) {
        adapter.submitList(data)
        val newPosition: Int = data.size
        adapter.notifyItemInserted(newPosition)
        binding.listItem.smoothScrollToPosition(newPosition)
    }

    private fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return currentDateTime.format(formatter)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
