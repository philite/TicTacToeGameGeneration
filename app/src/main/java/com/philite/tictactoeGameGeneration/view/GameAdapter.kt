package com.philite.tictactoeGameGeneration.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.philite.tictactoeGameGeneration.databinding.ViewItemTicTacToeBinding
import com.philite.tictactoeGameGeneration.model.ItemData

class GameAdapter(
    private val itemWidth: Int,
    private val itemHeight: Int,
    private val list: ArrayList<ItemData>,
    private val listener: OnItemViewClickListener
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewItemTicTacToeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.layoutParams.width = itemWidth
        binding.root.layoutParams.height = itemHeight
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit

    inner class ViewHolder(binding: ViewItemTicTacToeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemViewFlipper.setOnClickListener {
                listener.onClick(it, list[layoutPosition])
            }
        }
    }
}
