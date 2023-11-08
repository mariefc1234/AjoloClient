package com.moviles.axoloferiaxml.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moviles.axoloferiaxml.MainActivity
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.FragmentHomeBinding
import com.moviles.axoloferiaxml.ui.home.adapters.StallAdapter
import com.moviles.axoloferiaxml.ui.home.adapters.StallAdapterListener
import com.moviles.axoloferiaxml.ui.login.LoggedInUserView

class HomeFragment : Fragment(), StallAdapterListener {

    private val viewAdapter by lazy {
        StallAdapter(stallList, this)
    }
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var  stallViewModel: HomeViewModel

    private val stallList = mutableListOf<Stall.StallList.StallData>()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        val intent = Intent(context, StallDetailActivity::class.java)
        val bundle = Bundle()
        val gson = Gson()
        val stallJson = gson.toJson(stall)
        bundle.putString("stall", stallJson)
        intent.putExtra("stall_key",bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}