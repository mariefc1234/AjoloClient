package com.moviles.axoloferiaxml.ui.home_admin.admin_panel.employees_admin

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AlertDialog
import com.moviles.axoloferiaxml.R

class EmployeeAdminFragment : Fragment() {

    private lateinit var spinner: Spinner
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

        val btnRegister = dialogView.findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val username = dialogView.findViewById<EditText>(R.id.editTextUsername).text.toString()
            val password = dialogView.findViewById<EditText>(R.id.editTextPassword).text.toString()
            dialog.dismiss()
        }
        dialog.show()
    }
}
