package com.moviles.axoloferiaxml.ui.usermore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoreUserViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is More Fragment"
    }
    val text: LiveData<String> = _text
}