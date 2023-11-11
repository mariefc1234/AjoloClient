package com.moviles.axoloferiaxml.ui.home_admin.admin_panel.employees_admin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.viewModelScope
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.EmployeeRepository
import com.moviles.axoloferiaxml.data.model.user_employee.EmployeeRequest
import com.moviles.axoloferiaxml.domain.GetEmployeeRegisterUseCase
import com.moviles.axoloferiaxml.ui.qr_user.QrUserViewModel
import kotlinx.coroutines.runBlocking

class EmployeeAdminFragment : Fragment() {


    private val getEmployeeRegisterUseCase = GetEmployeeRegisterUseCase()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_employee_admin, container, false)
        val button = root.findViewById<ImageButton>(R.id.newEmployeeButton)
        button.setOnClickListener {
            showDialog()
        }

        return root
    }

    private fun showDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_register_employee, null)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        builder.setCancelable(true)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val spinner = dialogView.findViewById<Spinner>(R.id.rolesSpinner)

        val spinnerAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.roles_array, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        val btnRegister = dialogView.findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val username = dialogView.findViewById<EditText>(R.id.editTextUsername).text.toString()
            val password = dialogView.findViewById<EditText>(R.id.editTextPassword).text.toString()
            val email = dialogView.findViewById<EditText>(R.id.editTextEmail).text.toString()
            val rol = 4

            runBlocking {
                val employeeRequest = EmployeeRequest(
                    email = email,
                    password = password,
                    username = username,
                    idRol = rol
                )

                registerEmployee(requireContext(), employeeRequest)
            }


        }
        dialog.show()
    }

    suspend fun registerEmployee(context: Context, employeeRequest : EmployeeRequest) {

        val keystoreHelper = KeystoreHelper(context)
        val result = getEmployeeRegisterUseCase(keystoreHelper,employeeRequest)

        Log.d("MyApp", result.toString())

        if (result != null) {
            val username = result.data.userInfo.username
            Toast.makeText(context, "si jalo $username", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, "no jalo", Toast.LENGTH_LONG).show()

        }
    }

}
