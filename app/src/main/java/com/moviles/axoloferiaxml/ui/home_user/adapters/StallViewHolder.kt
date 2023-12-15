package com.moviles.axoloferiaxml.ui.home_user.adapters

import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.ItemStallBinding
import com.squareup.picasso.Picasso

class StallViewHolder(private val binding: ItemStallBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(stall: Stall.StallList.StallData, listener: StallAdapterListener){

        with(binding){
            Picasso.get().load(stall?.image_url).into(image)
            name.text = stall?.name
            price.text = "$ ${stall?.cost}"
            starsLabel.text = stall?.id_stall_type.toString()
            description.text = stall?.description

            favoriteIcon.setOnClickListener {
                listener.setFavouriteStall(stall, binding)
            }

        }



        itemView.setOnClickListener {
            listener.onStallSelected(stall)
        }
    }
}