package com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PreviewHeartRateViewModel @Inject constructor(private val repository: AppRepository) :
    ViewModel() {
    var heartRate: HeartRate? = null

    private val _dataLiveData: MutableLiveData<List<HeartRate>> = MutableLiveData()
    val dataLiveData: LiveData<List<HeartRate>>
        get() = _dataLiveData


    fun initArgument(bundle: Bundle?) {
        val result = bundle?.getString(Utils.KEY_HEART_RATE)
        result?.let {
            heartRate = Gson().fromJson(it, HeartRate::class.java)
        }
    }

    fun initData() {
        if (heartRate != null) return

        runBlocking {
            heartRate = repository.getNewestHeartRate()
        }
    }

    fun getDataRecent() {
        viewModelScope.launch {
            val result = ArrayList(repository.getRecentHeartRate())
            _dataLiveData.postValue(result)
        }
    }
}