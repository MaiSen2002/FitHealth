package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.preview

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PreviewBloodPressureViewModel @Inject constructor(private val bpRepository: AppRepository) :
    ViewModel() {
    var bloodPressure: BloodPressure? = null

    private val _dataLiveData: MutableLiveData<List<BloodPressure>> = MutableLiveData()
    val dataLiveData: LiveData<List<BloodPressure>>
        get() = _dataLiveData

    fun initArguments(bundle: Bundle?) {
        val dataText = bundle?.getString(Utils.KEY_BLOOD_PRESSURE)
        dataText?.let {
            bloodPressure = Gson().fromJson(it, BloodPressure::class.java)
        }
    }

    fun initData(context: Context) {
        runBlocking {
            bloodPressure = bpRepository.getNewestBloodPressure(context)
        }
    }

    fun getRecentBloodPressure() {
        viewModelScope.launch {
            val data = ArrayList(bpRepository.getRecentBloodPressure())
            _dataLiveData.postValue(data)
        }
    }

}