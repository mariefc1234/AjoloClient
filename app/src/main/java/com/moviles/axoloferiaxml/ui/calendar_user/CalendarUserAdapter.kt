package com.moviles.axoloferiaxml.ui.calendar_user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.calendar.EventsResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CalendarUserAdapter(var context : Context, var listEvents: EventsResponse)
    : RecyclerView.Adapter<CalendarUserAdapter.EventsViewHolder>()  {


    inner class EventsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvNameEventValue = itemView.findViewById(R.id.tvNameEventValue) as TextView
        val tvDateEventValue = itemView.findViewById(R.id.tvDateEventValue) as TextView
        val tvCostEventValue = itemView.findViewById(R.id.tvCostEventValue) as TextView
        val tvLocationEventValue = itemView.findViewById(R.id.tvLocationEventValue) as TextView
        val tvHourEventsValue = itemView.findViewById(R.id.tvHourEventsValue) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_events, parent, false)
        return EventsViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listEvents.data.event.size
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val events = listEvents.data.event[position]
        holder.tvNameEventValue.text = events.name
        holder.tvCostEventValue.text = events.cost.toString()
        holder.tvLocationEventValue.text = events.location

        if(events.timeEvent == null){
            holder.tvHourEventsValue.text = "N/A"
        }else{
            holder.tvHourEventsValue.text = events.timeEvent
        }

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        try {
            val date: Date = inputFormat.parse(events.dateEvent)
            val formattedDate: String = outputFormat.format(date)
            holder.tvDateEventValue.text = formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}