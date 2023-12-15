package com.moviles.axoloferiaxml.ui.stall_management.create_stall

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.support.annotation.StringRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import com.moviles.axoloferiaxml.data.model.Employee
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.data.model.StallCreate
import com.moviles.axoloferiaxml.data.model.User
import com.moviles.axoloferiaxml.databinding.FragmentCreateStallBinding
import com.moviles.axoloferiaxml.ui.home_user.StallDetailFragmentArgs
import com.moviles.axoloferiaxml.ui.reviews.AddReviewFragmentArgs

class CreateStallFragment: Fragment() {


    private lateinit var  stallViewModel: StallViewModel

    private var _binding: FragmentCreateStallBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateStallBinding.inflate(inflater, container, false)
        val root: View = binding.root
        stallViewModel = ViewModelProvider(this).get(StallViewModel::class.java)

        stallViewModel.stallResult.observe(viewLifecycleOwner, Observer {
            val stallResult = it ?: return@Observer

            if (stallResult.error != null) {
                showStallFailed(stallResult.error)
            }
            if (stallResult.success != null) {
                showStallSuccess(stallResult.success)
            }

        })
        binding.stallStallholder.setOnClickListener {
            navigateToStallHolder()
        }

        binding.stallSave.setOnClickListener {
            createStall()
        }

        binding.stallUpdate.setOnClickListener {
            updateStall()
        }


        val stallJson: String? = CreateStallFragmentArgs.fromBundle(requireArguments()).stall
        val stallHolderJson = CreateStallFragmentArgs.fromBundle(requireArguments()).stallholder


        Log.d("stalljson", stallJson!!)
        if(!(stallJson.isNullOrEmpty() || stallJson.isBlank())){
            fillStallToUpdate(stallJson)
            binding.stallUpdate.visibility = View.VISIBLE
            binding.stallSave.visibility = View.GONE
        } else {
            binding.stallUpdate.visibility = View.GONE
            binding.stallSave.visibility = View.VISIBLE
        }
        if(!(stallHolderJson.isNullOrEmpty() || stallHolderJson.isBlank())){
            fillStallHolderField(stallHolderJson)
        }

        return root
    }

    private fun fillStallHolderField(stallHolderJson: String?) {
        val gson = Gson()
        val stallHolder = gson.fromJson(stallHolderJson, Employee.EmployeeList.EmployeeInfo::class.java)
        binding.stallStallholder.text = stallHolder.username
        saveIdInPreferences(stallHolder)
    }

    private fun fillStallToUpdate(stallJson: String?) {
        val gson = Gson()
        val stall = gson.fromJson(stallJson, Stall.StallList.StallData::class.java)
        with(binding) {
            stallName.setText(stall.name ?: "")
            stallMinimunHeight.setText(stall.minimun_height_cm.toString())
            stallDescription.setText(stall.description)
            stallCost.setText(stall.cost.toString())
            stallStallholder.setText(stall.uuidEmployeer)
        }
    }

    private fun saveIdInPreferences(stallHolder: Employee.EmployeeList.EmployeeInfo) {
        val preference = "SOM"

        val prefs = requireActivity().getSharedPreferences(preference, MODE_PRIVATE)
        val name = prefs.getString("uuid_StallHolder_Create", "NULL") ?: "NULL"
        Log.d("uuid_StallHolder_Create", name)

        val editor = requireActivity().getSharedPreferences(preference, MODE_PRIVATE).edit()
        editor.remove("uuid_StallHolder_Create")
        editor.apply()
        Log.d("stallholder id", stallHolder.uuid)
        editor.putString("uuid_StallHolder_Create", stallHolder.uuid)
        editor.apply()

        val prefs2 = requireActivity().getSharedPreferences(preference, MODE_PRIVATE)
        val name2 = prefs2.getString("uuid_StallHolder_Create", "NULL") ?: "NULL"
        Log.d("uuid_StallHolder_Create", name2)
    }

    private fun navigateToStallHolder() {
        val navController = NavHostFragment.findNavController(this)
        val stall = getStallDataFromFields()
        val gson = Gson()
        var stallJson = ""
        if (stall != null) {
            stallJson = gson.toJson(stall)

        }
        val action = CreateStallFragmentDirections.actionCreateStallFragmentToStallHolderFragment(stallJson)
        navController.navigate(action)
    }

    private fun getStallDataFromFields(): StallCreate {
        with(binding){
            val name: String = stallName.text.toString()
            val minimunHeight: String = stallMinimunHeight.text.toString()
            val description: String = stallDescription.text.toString()
            val cost: String = stallCost.text.toString()
            val uuidEmployer: String = getIdInPreferences()

            val minimunHeightInt: Int = if(minimunHeight.isBlank()) 0 else minimunHeight.toInt()
            val costInt = if(cost.isBlank()) 0 else cost.toInt()
            val stallJson: String? = CreateStallFragmentArgs.fromBundle(requireArguments()).stall
            val gson = Gson()
            val id = gson.fromJson(stallJson, StallCreate::class.java).id ?: 0
            val stall = StallCreate(
                id = id,
                id_stall_type = 1,
                name = name,
                minimun_height_cm = minimunHeightInt,
                description = description,
                cost = costInt,
                uuidEmployeer = uuidEmployer
            )
            return stall
        }
    }


    private fun createStall() {

        val stall = getStallDataFromFields()
        stallViewModel.createStall(stall, requireActivity())
    }

    private fun updateStall() {
        val stall = getStallDataFromFields()
        stallViewModel.updateStall(stall, requireActivity())
    }

    private fun getIdInPreferences(): String {
        val preference = "SOM"

        val prefs = activity?.getSharedPreferences(preference, MODE_PRIVATE)
        val name = prefs?.getString("uuid_StallHolder_Create", "NULL") ?: "NULL"
        Log.d("uuid_StallHolder_Create", name)
        return name
    }

    private fun showStallSuccess(success: GenericResponse) {
        Toast.makeText(context, "EXCEP${success.message}", Toast.LENGTH_LONG).show()
    }

    private fun showStallFailed(@StringRes error: Int) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }


//    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
//        this.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(editable: Editable?) {
//                afterTextChanged.invoke(editable.toString())
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//        })
//    }
}

