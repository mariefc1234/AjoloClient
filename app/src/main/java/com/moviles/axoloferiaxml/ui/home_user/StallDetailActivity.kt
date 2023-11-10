package com.moviles.axoloferiaxml.ui.home_user

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.moviles.axoloferiaxml.R

class StallDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val bundle = intent.getBundleExtra("stall_key")


        setContentView(R.layout.activity_main_user)


        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = StallDetailFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.navHostFragmentUser, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}