package com.moviles.axoloferiaxml.ui.home

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

    private val stallList = mutableListOf<Stall.StallData>()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = viewManager
        binding.recyclerView.adapter = viewAdapter

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        stallViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        stallViewModel.stallResult.observe(viewLifecycleOwner, Observer {
            val stallResult = it ?: return@Observer

            if (stallResult.error != null) {
                showStallFailed(stallResult.error)
            }
            if (stallResult.success != null) {
                loadStallList(stallResult.success)
            }

            Log.d("xd1", stallResult.success.toString())

        })
        getStalls()
        return root
    }

    private fun getStalls() {
        Log.d("xd1", "1")
        stallViewModel.getStallList(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJmZDQ1YzYxYi02MmM1LTQ1MzYtYjFjMy1iYWIwMjE3M2Y1N2UiLCJ1c2VyQWdlbnQiOiJQb3N0bWFuUnVudGltZS83LjM0LjAiLCJ1c2VySXAiOiI6OmZmZmY6MTkyLjE2OC4wLjMiLCJpYXQiOjE2OTg3NDA4NzMsImV4cCI6MTcwMzkyNDg3M30.W-uAiU-rj8LsoJac-YVt9dDzhFq0W00zW1o_BWxAYoweyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJmZDQ1YzYxYi02MmM1LTQ1MzYtYjFjMy1iYWIwMjE3M2Y1N2UiLCJ1c2VyQWdlbnQiOiJQb3N0bWFuUnVudGltZS83LjM0LjAiLCJ1c2VySXAiOiI6OmZmZmY6MTkyLjE2OC4wLjMiLCJpYXQiOjE2OTg3NDA4NzMsImV4cCI6MTcwMzkyNDg3M30.W-uAiU-rj8LsoJac-YVt9dDzhFq0W00zW1o_BWxAYow",
            this.requireContext()
        )
    }

    private fun loadStallList(model: StallView) {
        stallList.addAll(model.stall)
        Toast.makeText(
            context,
            "funciono",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showStallFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStallSelected(stall: Stall.StallData) {
        TODO("Not yet implemented")
    }
}