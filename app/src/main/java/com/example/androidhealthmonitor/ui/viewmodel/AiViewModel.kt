package com.example.androidhealthmonitor.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.androidhealthmonitor.data.db.Patient
import com.example.androidhealthmonitor.util.AiAnalyzer
import kotlinx.coroutines.launch

class AiViewModel(app: Application) : AndroidViewModel(app) {
    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    fun analyze(patient: Patient) {
        viewModelScope.launch {
            _result.postValue("⏳ Analyzing with AI...")
            val res = AiAnalyzer.analyze(getApplication(), patient)
            _result.postValue(res)
        }
    }
}
