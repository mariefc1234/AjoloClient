package com.moviles.axoloferiaxml.ui.more_user.shopping_history_user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.sales.GetSalesResponse
import com.moviles.axoloferiaxml.data.model.sales.SalePostResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ShoppingHistoryUserAdapter (var context : Context,  var listShops: GetSalesResponse): RecyclerView.Adapter<ShoppingHistoryUserAdapter.ShopsViewHolder>()  {

    inner class ShopsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val card = itemView.findViewById(R.id.cardSales) as ImageView
        val cash = itemView.findViewById(R.id.cashSales) as ImageView

        val tvPaymentMethodValue = itemView.findViewById(R.id.tvPaymentMethodValue) as TextView
        val tvAjolosValue = itemView.findViewById(R.id.tvAjolosValue) as TextView
        val tvCostValue = itemView.findViewById(R.id.tvCostValue) as TextView
        val tvCouponValue = itemView.findViewById(R.id.tvCouponValue) as TextView
        val tvDateValue = itemView.findViewById(R.id.tvDateValue) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopsViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_shops, parent, false)
        return ShopsViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listShops.data.shoppings.size
    }

    override fun onBindViewHolder(holder: ShopsViewHolder, position: Int) {
        val sales = listShops.data.shoppings[position]

        if (sales.paymentMethod == 2){
            holder.card.isVisible = false
            holder.tvPaymentMethodValue.text = "Cash"
        }else{
            holder.cash.isVisible = false
            holder.card.isVisible = true
            holder.tvPaymentMethodValue.text = "Credit Card"
        }
        holder.tvAjolosValue.text = sales.coins.toString()
        holder.tvCostValue.text = sales.cost.toString()
        holder.tvCouponValue.text = sales.couponName

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        try {
            val date: Date = inputFormat.parse(sales.createdAt)
            val formattedDate: String = outputFormat.format(date)
            holder.tvDateValue.text = formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }


}