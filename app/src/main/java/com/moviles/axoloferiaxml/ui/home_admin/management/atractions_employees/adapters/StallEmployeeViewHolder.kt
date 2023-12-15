package com.moviles.axoloferiaxml.ui.home_user.adapters

import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.ItemEmployeeStallBinding
import com.moviles.axoloferiaxml.databinding.ItemStallBinding
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import kotlin.math.roundToInt

class StallEmployeeViewHolder(private val binding: ItemEmployeeStallBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(stall: Stall.StallList.StallData, listener: StallEmployeeAdapterListener){

        with(binding){
            Picasso.get().load(stall?.image_url).into(image)
            name.text = stall?.name
            price.text = "$ ${stall?.cost}"

            val format = DecimalFormat("#.00")

            starsLabel.text = format.format(stall?.points).toString()
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