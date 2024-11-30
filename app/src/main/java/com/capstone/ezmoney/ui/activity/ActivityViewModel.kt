package com.capstone.ezmoney.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is activity Fragment"
    }
    val text: LiveData<String> = _text
}