package com.moviles.axoloferiaxml.ui.home_admin.admin_panel.employees_admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.model.user_employee.Employee
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeAdd
import com.moviles.axoloferiaxml.data.network.user_employee.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeEdit
import com.moviles.axoloferiaxml.databinding.FragmentEmployeeAdmBinding
import kotlin.math.log

class EmployeeAdmFragment : Fragment(), EmployeeAdapter.OnItemClicked {
    lateinit var binding: FragmentEmployeeAdmBinding
    lateinit var adatador: EmployeeAdapter

    var listEmployee = Employee(
        status = "Success",
        message = "",
        data = Employee.EmployeeList(users = mutableListOf())
    )

    var employeeAdd = EmployeeAdd("","","",0,)
    var isEditating = false
    var uuid = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEmployeeAdmBinding.inflate(inflater, container, false)

        binding.rvUsuarios.layoutManager = LinearLayoutManager(context)
        setupRecyclerView()
        getEmployees()

        ocultarBarraNavegacion()


        binding.btnAddUpdate.setOnClickListener{
            val isValidated = validateFields()
            Log.e("btnUpdate", "Entro")

            if(isValidated){
                if (!isEditating) {
                    addEmployee()
                } else {
                    Log.e("before funcion", "Entro")
                    actualizarUsuario()
                }
            }else {
                Toast.makeText(requireContext(), "Se deben llenar los campos", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    private fun addEmployee() {

        this.employeeAdd.username = binding.etNombre.text.toString()
        this.employeeAdd.email = binding.etEmail.text.toString()
        this.employeeAdd.ps = binding.password.text.toString()
        this.employeeAdd.idRol = 4

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClient.webService.addUsers(keystoreHelper,employeeAdd)
                if (call.isSuccessful) {
                    getEmployees()
                    cleanFields()
                    cleanObject()

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

    private fun cleanObject() {
        this.employeeAdd.username = ""
        this.employeeAdd.email = ""
        this.employeeAdd.ps = ""
        this.employeeAdd.idRol = -1
    }

    private fun cleanFields() {
        requireActivity().runOnUiThread {
            binding.etNombre.setText("")
            binding.etEmail.setText("")
            binding.password.setText("")
            binding.spinner.isEnabled = true
            binding.password.isEnabled = true
            binding.password.isFocusable = true
            binding.password.isFocusableInTouchMode = true
        }
    }

    private fun validateFields(): Boolean  {
        return !(binding.etNombre.text.isNullOrEmpty() || binding.etEmail.text.isNullOrEmpty())
    }

    private fun ocultarBarraNavegacion() {
        if (activity is MainActivity) {
            val tuActividad = activity as MainActivity
            tuActividad.ocultarBarraNavegacion()
        }
    }


    fun setupRecyclerView() {
        adatador = EmployeeAdapter(this, listEmployee)
        binding.rvUsuarios.adapter = adatador
        adatador.setOnClick(this)
    }

    fun getEmployees() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keystoreHelper = KeystoreHelper(requireContext()).getToken()
                val call = RetrofitClient.webService.getUsers(keystoreHelper)
                if (call.isSuccessful) {
                    val employees = call.body()
                    employees?.let {
                        listEmployee = it
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


    fun actualizarUsuario() {
        Log.e("enFuncion", "Entro")
        var employeeEdit = EmployeeEdit(binding.etNombre.text.toString(), binding.etEmail.text.toString())
        val keystoreHelper = KeystoreHelper(requireContext()).getToken()
        Log.e("beforesucces", "Entro")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = RetrofitClient.webService.updateUser(keystoreHelper, uuid, employeeEdit)
                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        getEmployees()
                        cleanFields()
                        cleanObject()
                        binding.btnAddUpdate.setText("Add employee")
                        isEditating = false
                        Toast.makeText(requireContext(), "${call.body()!!.data.username} fue actualizado", Toast.LENGTH_LONG).show()
                    } else {
                        Log.e("API_CALL_UPDATE", "Error: ${call.code()}, ${call.errorBody()?.string()}")
                        Toast.makeText(requireContext(), "ERROR CONSULTAR TODOS", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: HttpException) {
                Log.e("API_CALL_ERROR_Update", "HTTP Exception: ${e.code()}, ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de autenticación: ${e.response()?.message()}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    override fun editarUsuario(usuario: Employee.EmployeeList.EmployeeInfo) {
        binding.etNombre.setText(usuario.username)
        binding.etEmail.setText(usuario.email)
        binding.password.setText(usuario.uuid)
        binding.spinner.isEnabled = false
        binding.password.isEnabled = false
        binding.password.isFocusable = false
        binding.password.isFocusableInTouchMode = false
        binding.btnAddUpdate.setText("Actualizar Usuario")
        binding.btnAddUpdate.backgroundTintList = resources.getColorStateList(R.color.purple_1)
        this.uuid = usuario.uuid
        isEditating = true
    }

    @SuppressLint("SuspiciousIndentation")
    override fun borrarUsuario(uuid: String) {
        val keystoreHelper = KeystoreHelper(requireContext()).getToken()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = RetrofitClient.webService.borrarUsuario(keystoreHelper, uuid)
                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        Toast.makeText(requireContext(), "Eliminado correctamente", Toast.LENGTH_LONG).show()
                        getEmployees()
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



