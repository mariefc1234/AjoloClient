package com.moviles.axoloferiaxml.ui.home_user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.FragmentHomeUserBinding
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallAdapter
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallAdapterListener

class HomeUserFragment : Fragment(), StallAdapterListener {

    private val viewAdapter by lazy {
        StallAdapter(stallList, this)
    }
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var stallViewModel: HomeViewModel

    private val stallList = mutableListOf<Stall.StallList.StallData>()

    private var _binding: FragmentHomeUserBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = viewManager
        binding.recyclerView.adapter = viewAdapter
        stallViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        stallViewModel.stallResult.observe(viewLifecycleOwner, Observer {
            val stallResult = it ?: return@Observer

            if (stallResult.error != null) {
                showStallFailed(stallResult.error)
            }
            if (stallResult.success != null) {
                loadStallList(stallResult.success)
            }

        })
        getStalls()
        return root
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

    override fun onStallSelected(stall: Stall.StallList.StallData) {
        val gson = Gson()
        val stallJson = gson.toJson(stall)
        Log.d("stall", stallJson)
        val navController = findNavController(this)
        val action = HomeUserFragmentDirections.actionHomeUserFragmentToStallDetailUserFragment(stallJson)
        navController.navigate(action)
    }
}