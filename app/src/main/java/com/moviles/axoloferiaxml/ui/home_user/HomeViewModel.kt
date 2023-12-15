package com.moviles.axoloferiaxml.ui.home_user


import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.database.AxoloferiaDB
import com.moviles.axoloferiaxml.data.database.StallFavorite
import com.moviles.axoloferiaxml.data.model.Stall
import com.moviles.axoloferiaxml.domain.GetStallListUseCase
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){

    private val _stallResult = MutableLiveData<StallResult>()
    val stallResult: LiveData<StallResult> = _stallResult

    private val _stallFavoriteResult = MutableLiveData<MutableList<StallFavorite>>()
    val stallFavoriteResult: LiveData<MutableList<StallFavorite>> = _stallFavoriteResult

    private val getStallListUseCase = GetStallListUseCase()

    fun getStallList(context: Context) {
        viewModelScope.launch {
            try {
                val keystoreHelper = KeystoreHelper(context)
                val result = getStallListUseCase(keystoreHelper)
                if (result != null) {
                    _stallResult.value = 
                        StallResult(success = result.stallData.stall.let { StallView( stall = it!!) })
                    _stallResult.value?.success?.stall?.forEach {
                        Log.d("stall", "$it")
                    }
                } else {
                    _stallResult.value =
                        StallResult(error = 0)
                    Toast.makeText(context, "EXCEP$result", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "EXCEP", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun setStallAsFavorite(context: Context, fragment: Fragment, stall: Stall.StallList.StallData){
        val room = Room
            .databaseBuilder(context, AxoloferiaDB::class.java, "axoloferia")
            .build()

        fragment.lifecycleScope.launch {

            val stallInsert = StallFavorite(
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
                points = stall.points
            )
            room.stallFavoriteDAO().insert(stallInsert)
        }
    }

    fun getFavouriteStalls(context: Context, fragment: Fragment) {
        val room = Room
            .databaseBuilder(context, AxoloferiaDB::class.java, "axoloferia")
            .build()

        fragment.lifecycleScope.launch {

            val stalls = room.stallFavoriteDAO().getAllStalls() as MutableList
            Log.d("Songs", stalls.toString())
            _stallFavoriteResult.value = stalls
        }
    }
}