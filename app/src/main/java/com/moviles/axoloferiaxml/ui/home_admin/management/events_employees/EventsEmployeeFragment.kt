package com.moviles.axoloferiaxml.ui.home_admin.management.events_employees

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.calendar.EventsPost
import com.moviles.axoloferiaxml.data.model.calendar.EventsResponse
import com.moviles.axoloferiaxml.data.model.offers.OffersPost
import com.moviles.axoloferiaxml.data.network.calendar.RetrofitClientEvents
import com.moviles.axoloferiaxml.data.network.offers.RetrofitClientOffers
import com.moviles.axoloferiaxml.databinding.FragmentEventsEmployeeBinding
import com.moviles.axoloferiaxml.databinding.FragmentOffersEmployeesBinding
import com.moviles.axoloferiaxml.ui.calendar_user.CalendarUserAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date

class EventsEmployeeFragment : Fragment(), EventsEmployeeAdapter.OnItemClicked {

    lateinit var binding: FragmentEventsEmployeeBinding
    lateinit var adatador: EventsEmployeeAdapter

    var listOfEvents = EventsResponse(
        status = "Success",
        msg = "",
        data = EventsResponse.Data(event = arrayListOf())
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsEmployeeBinding.inflate(inflater, container, false)

        binding.rvEvents.layoutManager = LinearLayoutManager(context)

        setupRecyclerView()
        getEvents()

        ocultarBarraNavegacion()

        binding.addEvent.setOnClickListener {
            showDialog()
        }

        return  binding.root
    }

    @SuppressLint("MissingInflatedId", "CutPasteId")
    private fun showDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_events, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        builder.setCancelable(true)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCancelEvent = dialogView.findViewById<Button>(R.id.btnCancelEvent)
        val btnRegisterEvent = dialogView.findViewById<Button>(R.id.btnRegisterEvent)

        btnCancelEvent.setOnClickListener {
            dialog.dismiss()
        }

        btnRegisterEvent.setOnClickListener {

            val etNameEvent = dialogView.findViewById<EditText>(R.id.etNameEvent).text.toString()
            val etCostEvent = dialogView.findViewById<EditText>(R.id.etCostEvent).text.toString()
            val etLocationEvent = dialogView.findViewById<EditText>(R.id.etLocationEvent).text.toString()
            val dateEvent = dialogView.findViewById<DatePicker>(R.id.dateEvent)
            val timeEvent = dialogView.findViewById<TimePicker>(R.id.timeEvent)

            val year = dateEvent.year
            val month = dateEvent.month + 1
            val dayOfMonth = dateEvent.dayOfMonth
            val formattedDate = String.format("%04d-%02d-%02d", year, month, dayOfMonth)

            val hour = timeEvent.hour
            val minute = timeEvent.minute
            val formattedTime = String.format("%02d:%02d:00", hour, minute)

            if (etNameEvent.isBlank() || etCostEvent.isBlank() || etLocationEvent.isBlank()) {
                Toast.makeText(context, "Todos los campos deben ser completados", Toast.LENGTH_SHORT).show()
            } else {
                runBlocking {
                    val eventPost = EventsPost(
                        name = etNameEvent,
                        cost = etCostEvent.toInt(),
                        dateEvent = formattedDate,
                        location = etLocationEvent,
                        timeEvent = formattedTime
                    )

                    addEvents(requireContext(), eventPost, dialog)
                }
            }

        }

        dialog.show()
    }

    suspend fun addEvents(context: Context, eventPost : EventsPost, dialog : Dialog) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClientEvents.webServiceEvents.addEvents(keystoreHelper,eventPost)
                if (call.isSuccessful) {
                    getEvents()
                    dialog.dismiss()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Registro realizado", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e("API_CALL_ERROR", "Error: ${call.code()}, ${call.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "ERROR al registrar", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: HttpException) {
                Log.e("API_CALL_ERROR_POST", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de autenticación: ${e.response()?.message()}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val currentDate = Date()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val formattedDate = dateFormat.format(currentDate)
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClientEvents.webServiceEvents.getEvents(keystoreHelper,formattedDate)
                if (call.isSuccessful) {
                    val sales = call.body()
                    sales?.let {
                        listOfEvents = it
                        withContext(Dispatchers.Main) {
                            setupRecyclerView()
                        }
                    }
                } else {
                    Log.e("API_CALL_ERROR", "Error: ${call.code()}, ${call.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "ERROR CONSULTAR TODOS", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: HttpException) {
                Log.e("API_CALL_ERROR", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de autenticación: ${e.response()?.message()}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adatador = EventsEmployeeAdapter(requireContext(), listOfEvents)
        binding.rvEvents.adapter = adatador
        adatador.setOnClick(this)
    }

    private fun ocultarBarraNavegacion() {
        if (activity is MainActivity) {
            val tuActividad = activity as MainActivity
            tuActividad.ocultarBarraNavegacion()
        }
    }

    override fun deleteEvent(id: Int) {
        val keystoreHelper = KeystoreHelper(requireContext()).getToken()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = RetrofitClientEvents.webServiceEvents.deleteEvents(keystoreHelper,id)
                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        Toast.makeText(requireContext(), "Eliminado correctamente", Toast.LENGTH_LONG).show()
                        getEvents()
                    } else {
                        Log.e("API_CALL_DELETE", "Error: ${call.code()}, ${call.errorBody()?.string()}")
                        Toast.makeText(requireContext(), "Error al eliminar usuario", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("EXCEPTION", "Exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error inesperado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}