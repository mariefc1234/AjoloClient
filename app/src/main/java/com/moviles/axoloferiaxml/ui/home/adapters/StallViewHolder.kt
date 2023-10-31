package com.moviles.axoloferiaxml.ui.home.adapters

import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.ItemStallBinding

class StallViewHolder(private val binding: ItemStallBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(stall: Stall.StallData, listener: StallAdapterListener){

        with(binding){

            name.text = stall?.name
            price.text = stall?.cost
            starsLabel.text = stall?.id_stall_type
            description.text = stall?.description
        }

        itemView.setOnClickListener {
            listener.onStallSelected(stall)
        }
    }
}