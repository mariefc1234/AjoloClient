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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.gson.Gson
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.database.AxoloferiaDB
import com.moviles.axoloferiaxml.data.database.StallFavorite
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.databinding.FragmentHomeUserBinding
import com.moviles.axoloferiaxml.databinding.ItemStallBinding
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallAdapter
import com.moviles.axoloferiaxml.ui.home_user.adapters.StallAdapterListener
import kotlinx.coroutines.launch

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
        with(binding) {
            buttonAllStalls.setOnClickListener {
                getStalls()
            }
            buttonAllStalls.setOnClickListener {
                getFavoriteStalls()
            }
        }
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

    override fun setFavouriteStall(stall: Stall.StallList.StallData, itemBinding: ItemStallBinding) {
        val stallFavorite = getFavoriteStallById(stall.id!!)
        if (stallFavorite == null) {
            itemBinding.favoriteIcon.setImageResource(R.drawable.favorite_fill)
//            setFavoriteStall(stall)
        } else {
            itemBinding.favoriteIcon.setImageResource(R.drawable.favorite)
//            removeFavoriteStall(stallFavorite)
        }
    }

    private fun setFavoriteStall(stall: Stall.StallList.StallData) {
        val room = Room
            .databaseBuilder(requireContext(), AxoloferiaDB::class.java, "axoloferia")
            .build()

        lifecycleScope.launch {
            val song = StallFavorite(
                id = stall.id,
                id_stall_type = stall.id_stall_type,
                name = stall.name,
                description = stall.description,
                image_url = stall.image_url,
                cost = stall.cost,
                minimun_height_cm = stall.minimun_height_cm,
                uuidEmployeer = stall.uuidEmployeer,
                enabled = stall.enabled,
                createdAt = stall.createdAt,
                updatedAt = stall.updatedAt,
            )
            room.stallFavoriteDAO().insert(song)
        }
    }

    private fun getFavoriteStalls() {
        val room = Room
            .databaseBuilder(requireContext(), AxoloferiaDB::class.java, "axoloferia")
            .build()

//        lifecycleScope.launch {
//            val otherSongs = room.stallFavoriteDAO().getFavoriteStalls()
//            Log.d("Songs", otherSongs.toString())
//        }
    }

    private fun getFavoriteStallById(id: Int): StallFavorite? {
        val room = Room
            .databaseBuilder(requireContext(), AxoloferiaDB::class.java, "axoloferia")
            .build()
        lateinit var otherSongs: StallFavorite
//        lifecycleScope.launch {
//            otherSongs = room.stallFavoriteDAO().getStallByID(id)
//            Log.d("Songs", otherSongs.toString())
//
//        }
        return otherSongs
    }

    private fun removeFavoriteStall(stall: StallFavorite) {
        val room = Room
            .databaseBuilder(requireContext(), AxoloferiaDB::class.java, "axoloferia")
            .build()

        lifecycleScope.launch {
            room.stallFavoriteDAO().remove(stall)
        }
    }

}