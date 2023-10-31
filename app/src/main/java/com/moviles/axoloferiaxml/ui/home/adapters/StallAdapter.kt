package com.moviles.axoloferiaxml.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.ItemStallBinding

interface StallAdapterListener{
    fun onStallSelected(stall: Stall.StallData)
}

class StallAdapter(private val items: MutableList<Stall.StallData>, private val listener: StallAdapterListener): RecyclerView.Adapter<StallViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemStallBinding.inflate(view, parent, false)
        return StallViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StallViewHolder, position: Int) {
        val stall = items[position]
        holder.bindData(stall, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}