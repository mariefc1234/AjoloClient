package com.moviles.axoloferiaxml.ui.home_user.adapters

import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.ItemEmployeeStallBinding
import com.moviles.axoloferiaxml.databinding.ItemStallBinding
import com.squareup.picasso.Picasso

class StallEmployeeViewHolder(private val binding: ItemEmployeeStallBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(stall: Stall.StallList.StallData, listener: StallEmployeeAdapterListener){

        with(binding){
            Picasso.get().load(stall?.image_url).into(image)
            name.text = stall?.name
            price.text = "$ ${stall?.cost}"
            starsLabel.text = stall?.id_stall_type.toString()
            description.text = stall?.description



            stallEdit.setOnClickListener {
                listener.onStallEditSelected(stall)
            }

            stallDelete.setOnClickListener {
                listener.onStallDeleteSelected(stall)
            }
        }

    }
}