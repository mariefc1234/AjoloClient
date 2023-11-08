package com.moviles.axoloferiaxml.ui.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.moviles.axoloferiaxml.R
import com.moviles.axoloferiaxml.data.model.Stall

class StallDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val bundle = intent.getBundleExtra("stall_key")


        setContentView(R.layout.activity_stall_detail)


        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = StallDetailFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_stall_detail, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}