package com.moviles.axoloferiaxml.ui.home_user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.ItemEmployeeStallBinding

interface StallEmployeeAdapterListener{
    fun onStallEditSelected(stall: Stall.StallList.StallData)
    fun onStallDeleteSelected(stall: Stall.StallList.StallData)
}

class StallEmployeeAdapter(private val items: MutableList<Stall.StallList.StallData>, private val listener: StallEmployeeAdapterListener): RecyclerView.Adapter<StallEmployeeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallEmployeeViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeStallBinding.inflate(view, parent, false)
        return StallEmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StallEmployeeViewHolder, position: Int) {
        val stall = items[position]
        holder.bindData(stall, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}