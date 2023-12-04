package com.moviles.axoloferiaxml.ui.home_admin.admin_panel.employees_admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.user_employee.Employee

class EmployeeAdapter(var context: EmployeeAdmFragment, var listEmployees: Employee): RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    private var onClick: OnItemClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_usuario, parent, false)
        return EmployeeViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listEmployees.data.users!!.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val usuario = listEmployees.data.users?.get(position)


        holder.tvIdUsuario.text = usuario!!.uuid
        holder.tvNombre.text = usuario.username
        holder.tvEmail.text = usuario.email

        holder.btnEditar.setOnClickListener {
            onClick?.editarUsuario(usuario)
        }

        holder.btnBorrar.setOnClickListener {
            onClick?.borrarUsuario(usuario.uuid)
        }

    }

    inner class EmployeeViewHolder(itemView: View):ViewHolder(itemView){
        val tvIdUsuario = itemView.findViewById(R.id.tvIdUsuario) as TextView
        val tvNombre = itemView.findViewById(R.id.tvNombre) as TextView
        val tvEmail = itemView.findViewById(R.id.tvEmail) as TextView
        val btnEditar = itemView.findViewById(R.id.btnEditar) as Button
        val btnBorrar = itemView.findViewById(R.id.btnBorrar) as Button
    }

    interface OnItemClicked {
        fun editarUsuario(usuario: Employee.EmployeeList.EmployeeInfo)
        fun borrarUsuario(uuid: String)
    }

    fun setOnClick(onClick: OnItemClicked?) {
        this.onClick = onClick
    }

}