package com.moviles.axoloferiaxml.ui.home_admin.management.offers_employees

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.offers.OffersGetAllsResponse


class OffersEmployeesAdapter(var context : Context, var listOffers: OffersGetAllsResponse): RecyclerView.Adapter<OffersEmployeesAdapter.OffersViewHolder>() {

    private var onClick: OnItemClicked? = null

    inner class OffersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tvCouponNameValue = itemView.findViewById(R.id.tvCouponNameValue) as TextView
        val tvTypeCouponValue = itemView.findViewById(R.id.tvTypeCouponValue) as TextView
        val tvMinAmountCouponValue = itemView.findViewById(R.id.tvMinAmountCouponValue) as TextView
        val tvValueCupponValue = itemView.findViewById(R.id.tvValueCupponValue) as TextView
        val btnDeleteCoupon = itemView.findViewById(R.id.btnDeleteCoupon) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_offers, parent, false)
        return OffersViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listOffers.data.size
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        val coupons = listOffers.data[position]
        if (coupons.idCouponType == 2){
            holder.tvTypeCouponValue.text = "Percentage"
        }else{
            holder.tvTypeCouponValue.text = "Gross discount"
        }
        holder.tvCouponNameValue.text = coupons.codeCoupon
        holder.tvMinAmountCouponValue.text = coupons.minimunAmount.toString()
        holder.tvValueCupponValue.text = coupons.valueCoupon.toString()

        holder.btnDeleteCoupon.setOnClickListener {
            onClick?.borrarUsuario(coupons.id!!)
        }
    }

    interface OnItemClicked {
        fun borrarUsuario(id : Int)
    }

    fun setOnClick(onClick: OnItemClicked?) {
        this.onClick = onClick
    }

}