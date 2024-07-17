package com.bloodpressure.bloodtracker.bptracker.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodpressure.bloodtracker.bptracker.domain.BloodDataModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugar
import com.bloodpressure.bloodtracker.bptracker.domain.BloodType
import com.bloodpressure.bloodtracker.bptracker.domain.BmiData
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.domain.HistoryAdsDataModel
import com.bloodpressure.bloodtracker.bptracker.helper.PreferenceHelper
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val _dataLiveData: MutableLiveData<List<BloodDataModel>> = MutableLiveData()
    val dataLiveData: LiveData<List<BloodDataModel>>
        get() = _dataLiveData

    private val _isSuccessLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessLiveData: LiveData<Boolean>
        get() = _isSuccessLiveData

    var bloodPressures = listOf<BloodPressure>()
    var bloodSugar = listOf<BloodSugar>()
    var heartRate = listOf<HeartRate>()
    var listBmi = listOf<BmiData>()

    fun initData() {
        val data = arrayListOf<BloodDataModel>()
        bloodPressures = listOf()
        bloodSugar = listOf()
        heartRate = listOf()
        listBmi = listOf()

        runBlocking {
            val bloodPressures = async { getAllDataBp() }
            val bloodSugars = async { getAllDataBs() }
            val heartRates = async { getAllDataHr() }
            val listBmi = async { getAllBmi() }

            val temp1 = bloodPressures.await()
            temp1.forEach {
                data.add(
                    BloodDataModel(
                        it.id,
                        it.date,
                        it.time,
                        BloodType.BLOOD_PRESSURE,
                        "",
                        it.isFileTemp
                    )
                )
            }


            val temp2 = bloodSugars.await()
            temp2.forEach {
                data.add(
                    BloodDataModel(
                        it.id,
                        it.date,
                        it.time,
                        BloodType.BLOOD_SUGAR,
                        "",
                        it.isFileTemp
                    )
                )
            }


            val temp3 = heartRates.await()
            temp3.forEach {
                data.add(
                    BloodDataModel(
                        it.id,
                        it.date,
                        it.time,
                        BloodType.HEART_RATE,
                        "",
                        it.isFileTemp
                    )
                )
            }

            val temp4 = listBmi.await()
            temp4.forEach {
                data.add(
                    BloodDataModel(
                        it.id,
                        it.date,
                        it.time,
                        BloodType.BMI,
                        "",
                        it.isFileTemp
                    )
                )
            }

            data.forEach {
                it.weekdays = SimpleDateFormat(
                    "EEEE",
                    Locale(preferenceHelper.getString(PreferenceHelper.PREF_CURRENT_LANGUAGE)!!)
                ).format(Date(it.date).time)

                _dataLiveData.postValue(data)
            }
        }
    }

    private suspend fun getAllDataBp(): List<BloodPressure> {
        bloodPressures = repository.getAllBP()
        return bloodPressures
    }

    private suspend fun getAllDataBs(): List<BloodSugar> {
        bloodSugar = repository.getAllBS()
        return bloodSugar
    }

    private suspend fun getAllDataHr(): List<HeartRate> {
        heartRate = repository.getAllHR()
        return heartRate
    }

    private suspend fun getAllBmi(): List<BmiData> {
        listBmi = repository.getAllBmi()
        return listBmi
    }

    fun deleteBloodPressure(bloodPressure: BloodPressure) {
        viewModelScope.launch {
            repository.deleteBloodPressure(bloodPressure)
            _isSuccessLiveData.postValue(true)
        }
    }

    fun deleteBloodSugar(bloodSugar: BloodSugar) {
        viewModelScope.launch {
            repository.deleteBloodSugar(bloodSugar)
            _isSuccessLiveData.postValue(true)
        }
    }

    fun deleteHeartRate(heartRate: HeartRate) {
        viewModelScope.launch {
            repository.deleteHeartRate(heartRate)
            _isSuccessLiveData.postValue(true)
        }
    }

    fun deleteBmiData(bmiData: BmiData) {
        viewModelScope.launch {
            repository.deleteBmi(bmiData)
            _isSuccessLiveData.postValue(true)
        }
    }

}