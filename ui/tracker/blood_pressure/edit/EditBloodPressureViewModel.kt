package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.edit

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodpressure.bloodtracker.bptracker.domain.BPInfoDataModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBloodPressureViewModel @Inject constructor(
    private val bpRepository: AppRepository
) :
    ViewModel() {

    var data = listOf<BPInfoDataModel>()
    var bloodPressure = BloodPressure()

    private val _bpInfoLiveData: MutableLiveData<BPInfoDataModel> = MutableLiveData()
    val bpInfoLiveData: LiveData<BPInfoDataModel>
        get() = _bpInfoLiveData

    private val _isSuccessLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessLiveData: LiveData<Boolean>
        get() = _isSuccessLiveData
    private val _dateLiveData: MutableLiveData<Long> = MutableLiveData()
    val dateLiveData: LiveData<Long>
        get() = _dateLiveData

    private val _timeLiveData: MutableLiveData<String> = MutableLiveData()
    val timeLiveData: LiveData<String>
        get() = _timeLiveData

    fun initData() {
        getInfoBloodPressure()
    }

    fun onTimeChange(hour: Int, minute: Int) {
        _timeLiveData.value = toTimeString(hour, minute)
        bloodPressure.time = toTimeString(hour, minute)
    }

    fun onDateChange(value: Long) {
        _dateLiveData.value = value
        bloodPressure.date = value

    }

    fun initArguments(bundle: Bundle?) {
        val dataText = bundle?.getString(Utils.KEY_BLOOD_PRESSURE)
        dataText?.let {
            bloodPressure = Gson().fromJson(it, BloodPressure::class.java)
        }
    }

    fun onSystolicChange(value: Int) {
        bloodPressure.systolic = value
        getInfoBloodPressure()
    }

    fun onDiastolicChange(value: Int) {
        bloodPressure.diastolic = value
        getInfoBloodPressure()
    }

    fun updateBloodPressure(note: String) {
        bloodPressure.apply {
            this.note = note
            this.status = _bpInfoLiveData.value!!.status
            this.icon = _bpInfoLiveData.value!!.icon
        }


        viewModelScope.launch {
            bpRepository.updateBloodPressure(bloodPressure)
            _isSuccessLiveData.postValue(true)
        }
    }

    private fun getInfoBloodPressure() {
        val systolic = bloodPressure.systolic
        val diastolic = bloodPressure.diastolic

        _bpInfoLiveData.value = if (systolic > 180 || diastolic > 120)
            data[4]
        else if (systolic in 140..180 || diastolic in 90..120)
            data[3]
        else if (systolic in 130..139 || diastolic in 80..89)
            data[2]
        else if (systolic in 120..129 && diastolic in 60..79)
            data[1]
        else data[0]
    }

}