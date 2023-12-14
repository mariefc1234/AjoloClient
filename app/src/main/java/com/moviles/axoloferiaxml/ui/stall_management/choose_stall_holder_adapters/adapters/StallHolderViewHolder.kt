package com.moviles.axoloferiaxml.ui.stall_management.choose_stall_holder_adapters.adapters

import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.data.model.Employee
import com.moviles.axoloferiaxml.databinding.ItemStallholderBinding
import com.squareup.picasso.Picasso

class StallHolderViewHolder (private val binding: ItemStallholderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(stallHolder: Employee.EmployeeList.EmployeeInfo, listener: StallHolderAdapterListener){

        with(binding){
            Picasso.get().load(stallHolder?.imageUrl).into(stallholderImage)
            stallholderName.text = stallHolder?.username
        }

        itemView.setOnClickListener {
            listener.onStallHolderSelected(stallHolder)
        }
    }
}