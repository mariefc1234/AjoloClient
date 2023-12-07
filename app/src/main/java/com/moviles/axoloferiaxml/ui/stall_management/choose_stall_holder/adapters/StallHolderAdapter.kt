package com.moviles.axoloferiaxml.ui.stall_management.choose_stall_holder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.data.model.Employee
import com.moviles.axoloferiaxml.databinding.ItemStallholderBinding
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallViewHolder

interface StallHolderAdapterListener{
    fun onStallHolderSelected(stallHolder: Employee.EmployeeList.EmployeeInfo)
}

class StallHolderAdapter (private val items: MutableList<Employee.EmployeeList.EmployeeInfo>, private val listener: StallHolderAdapterListener): RecyclerView.Adapter<StallHolderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallHolderViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemStallholderBinding.inflate(view, parent, false)
        return StallHolderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StallHolderViewHolder, position: Int) {
        val stall = items[position]
        holder.bindData(stall, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}