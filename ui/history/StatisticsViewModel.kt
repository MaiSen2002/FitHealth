package com.bloodpressure.bloodtracker.bptracker.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugar
import com.bloodpressure.bloodtracker.bptracker.domain.BmiData
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRateStatus
import com.bloodpressure.bloodtracker.bptracker.domain.StatisticsDataModel
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    private val _dataLiveData: MutableLiveData<StatisticsDataModel> = MutableLiveData()
    val dataLiveData: LiveData<StatisticsDataModel>
        get() = _dataLiveData

    var data: StatisticsDataModel = StatisticsDataModel()

    fun initData() {
        runBlocking {
            val bloodPressures = async { getAllDataBp() }
            val bloodSugars = async { getAllDataBs() }
            val heartRates = async { getAllDataHr() }
            val listBmi = async { getAllBmi() }

            data.bloodPressures = bloodPressures.await().ifEmpty {
                listOf(BloodPressure())
            }

            data.bloodSugars =
                bloodSugars.await().ifEmpty { listOf(BloodSugar(sugarConcentration = 5.0)) }

            data.heartRates = heartRates.await().ifEmpty {
                listOf(
                    HeartRate(
                        color = "$00C57E",
                        status = HeartRateStatus.NORMAL.ordinal
                    )
                )
            }

            data.listBmi = listBmi.await().ifEmpty {
                listOf(BmiData().apply {
                    bmi = 22.0
                })
            }

            _dataLiveData.postValue(data)
        }
    }

    private suspend fun getAllDataBp() = repository.getAllBP()

    private suspend fun getAllDataBs() = repository.getAllBS()

    private suspend fun getAllDataHr() = repository.getAllHR()

    private suspend fun getAllBmi() = repository.getAllBmi()
}