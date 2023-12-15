package com.moviles.axoloferiaxml.ui.home_admin.management.atractions_employees

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.FragmentAtractionsEmployeesBinding
import com.moviles.axoloferiaxml.databinding.FragmentHomeUserBinding
import com.moviles.axoloferiaxml.ui.home_user.HomeUserFragmentDirections
import com.moviles.axoloferiaxml.ui.home_user.HomeViewModel
import com.moviles.axoloferiaxml.ui.home_user.StallView
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallAdapter
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallEmployeeAdapter
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallEmployeeAdapterListener
import com.moviles.axoloferiaxml.ui.stall_management.create_stall.StallViewModel

class AtractionsEmployeesFragment : Fragment(), StallEmployeeAdapterListener {
    private val viewAdapter by lazy {
        StallEmployeeAdapter(stallList, this)
    }
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var  stallViewModel: HomeViewModel
    private lateinit var  stallManagementViewModel: StallViewModel

    private val stallList = mutableListOf<Stall.StallList.StallData>()

    private var _binding: FragmentAtractionsEmployeesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAtractionsEmployeesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = viewManager
        binding.recyclerView.adapter = viewAdapter
        stallViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        stallManagementViewModel = ViewModelProvider(this).get(StallViewModel::class.java)

        stallViewModel.stallResult.observe(viewLifecycleOwner, Observer {
            val stallResult = it ?: return@Observer

            if (stallResult.error != null) {
                showStallFailed(stallResult.error)
            }
            if (stallResult.success != null) {
                loadStallList(stallResult.success)
            }

        })
        stallManagementViewModel.stallDeleteResult.observe(viewLifecycleOwner, Observer {
            val stallDeleteResult = it ?: return@Observer
            if (stallDeleteResult.error != null) {
                showStallDeleteFailed(stallDeleteResult.error)
            }
            if (stallDeleteResult.success != null) {
                loadStallDelteList(stallDeleteResult.success)
            }
        })
        binding.stallCreate.setOnClickListener {
            navigateToCreateStallFragment(null)
        }

        getStalls()
        return root
    }

    private fun loadStallDelteList(success: GenericResponse) {
        getStalls()
    }

    private fun showStallDeleteFailed(error: Int) {
        Toast.makeText(context, "error no stall delete", Toast.LENGTH_SHORT).show()
    }

    private fun getStalls() {
        stallViewModel.getStallList(this.requireContext())
    }

    private fun loadStallList(model: StallView) {
        stallList.clear()
        stallList.addAll(model.stall)
        viewAdapter.notifyDataSetChanged()
    }

    private fun showStallFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStallEditSelected(stall: Stall.StallList.StallData) {
        navigateToCreateStallFragment(stall)
    }

    private fun navigateToCreateStallFragment(stall: Stall.StallList.StallData?) {
        val gson = Gson()
        var stallJson = ""
        if (stall != null) {
            stallJson = gson.toJson(stall)

        }
        Log.d("stall", stallJson)

        val navController = NavHostFragment.findNavController(this)
        val action = AtractionsEmployeesFragmentDirections.actionAtractionsEmployeesFragmentToCreateStallFragment(stallJson, null)
        navController.navigate(action)
    }

    override fun onStallDeleteSelected(stall: Stall.StallList.StallData) {
        stallManagementViewModel.deleteStall(this.requireContext(), stall.id!!)
        Toast.makeText(context, stall.name, Toast.LENGTH_SHORT).show()
    }

}