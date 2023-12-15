package com.moviles.axoloferiaxml.ui.stall_management.choose_stall_holder_adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moviles.axoloferiaxml.data.model.Employee
import com.moviles.axoloferiaxml.databinding.FragmentStallholderBinding
import com.moviles.axoloferiaxml.ui.stall_management.choose_stall_holder_adapters.adapters.StallHolderAdapter
import com.moviles.axoloferiaxml.ui.stall_management.choose_stall_holder_adapters.adapters.StallHolderAdapterListener
import com.moviles.axoloferiaxml.ui.stall_management.create_stall.StallViewModel

class StallHolderFragment : Fragment(), StallHolderAdapterListener {

    private val viewAdapter by lazy {
        StallHolderAdapter(stallList, this)
    }
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val stallList = mutableListOf<Employee.EmployeeList.EmployeeInfo>()

    private lateinit var  stallViewModel: StallViewModel

    private var _binding: FragmentStallholderBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStallholderBinding.inflate(inflater, container, false)
        val root: View = binding.root


        viewManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = viewManager
        binding.recyclerView.adapter = viewAdapter

        stallViewModel = ViewModelProvider(this).get(StallViewModel::class.java)

        stallViewModel.stallHolderResult.observe(viewLifecycleOwner, Observer {
            val stallResult = it ?: return@Observer

            if (stallResult.error != null) {
                showStallHolderFailed(stallResult.error)
            }
            if (stallResult.success != null) {
                showStallHolderSuccess(stallResult.success)
            }

        })
        getStallHolders()
        return root
    }

    private fun getStallHolders() {
        stallViewModel.getStallHolders(this.requireContext())
    }


    private fun showStallHolderSuccess(success: Employee) {
        stallList.clear()
        stallList.addAll(success.data.users!!)
        viewAdapter.notifyDataSetChanged()
        Toast.makeText(context, "EXCEP ${success.message} size: ${success.data.users?.size}", Toast.LENGTH_LONG).show()
    }

    private fun showStallHolderFailed(error: Int) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCreateStall(stallHolder: Employee.EmployeeList.EmployeeInfo) {
        val gson = Gson()
        val stallHolderJson = gson.toJson(stallHolder)
        Log.d("stallHolder", stallHolderJson)
        val navController = NavHostFragment.findNavController(this)
        val stallJson: String? = StallHolderFragmentArgs.fromBundle(requireArguments()).stall
        val action = StallHolderFragmentDirections.actionStallHolderFragmentToCreateStallFragment(
            stallJson,
            stallHolderJson
        )
        navController.navigate(action)
    }

    override fun onStallHolderSelected(stallHolder: Employee.EmployeeList.EmployeeInfo) {
        navigateToCreateStall(stallHolder)
    }
}