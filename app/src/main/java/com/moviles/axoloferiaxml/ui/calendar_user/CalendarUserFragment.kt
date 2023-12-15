package com.moviles.axoloferiaxml.ui.calendar_user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviles.axoloferiaxml.MainActivityUser
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.calendar.EventsResponse
import com.moviles.axoloferiaxml.data.model.sales.GetSalesResponse
import com.moviles.axoloferiaxml.data.network.calendar.RetrofitClientEvents
import com.moviles.axoloferiaxml.data.network.sales.RetrofitClientSales
import com.moviles.axoloferiaxml.databinding.FragmentCalendarUserBinding
import com.moviles.axoloferiaxml.databinding.FragmentEmployeeAdmBinding
import com.moviles.axoloferiaxml.ui.home_admin.admin_panel.employees_admin.EmployeeAdapter
import com.moviles.axoloferiaxml.ui.more_user.shopping_history_user.ShoppingHistoryUserAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date

class CalendarUserFragment : Fragment() {

    lateinit var binding: FragmentCalendarUserBinding
    lateinit var adatador: CalendarUserAdapter

    var listOfEvents = EventsResponse(
        status = "Success",
        msg = "",
        data = EventsResponse.Data(event = arrayListOf())
    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCalendarUserBinding.inflate(inflater, container, false)
        binding.rvEvents.layoutManager = LinearLayoutManager(context)
        setupRecyclerView()
        val uuid = (requireActivity() as MainActivityUser).getUserUuid()
        getEvents()

        return  binding.root
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
        adatador = CalendarUserAdapter(requireContext(), listOfEvents)
        binding.rvEvents.adapter = adatador
    }

}