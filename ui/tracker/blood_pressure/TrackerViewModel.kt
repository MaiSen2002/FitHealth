package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugar
import com.bloodpressure.bloodtracker.bptracker.domain.BmiData
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    var bloodPressures = listOf<BloodPressure>()
    var bloodSugars = listOf<BloodSugar>()
    var heartRates = listOf<HeartRate>()
    var weightBmis = listOf<BmiData>()

    private val _bpLiveData = MutableLiveData<BloodPressure>()
    val bpLiveData: LiveData<BloodPressure>
        get() = _bpLiveData

    fun initData() {
        runBlocking {
            getAllDataBp()
            getAllDataBs()
            getAllDataHr()
            getAllBmi()
        }
    }

    private suspend fun getAllDataBp(): List<BloodPressure> {
        bloodPressures = repository.getAllBP()
        return bloodPressures
    }

    private suspend fun getAllDataBs(): List<BloodSugar> {
        bloodSugars = repository.getAllBS()
        return bloodSugars
    }

    private suspend fun getAllDataHr(): List<HeartRate> {
        heartRates = repository.getAllHR()
        return heartRates
    }

    private suspend fun getAllBmi(): List<BmiData> {
        weightBmis = repository.getAllBmi()
        return weightBmis
    }
}