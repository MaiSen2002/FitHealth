package com.bloodpressure.bloodtracker.bptracker.repository

import android.content.Context
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.database.BloodPressureDao
import com.bloodpressure.bloodtracker.bptracker.database.BloodSugarDao
import com.bloodpressure.bloodtracker.bptracker.database.BmiDao
import com.bloodpressure.bloodtracker.bptracker.database.HeartRateDao
import com.bloodpressure.bloodtracker.bptracker.database.toModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugar
import com.bloodpressure.bloodtracker.bptracker.domain.BmiData
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRateStatus
import com.bloodpressure.bloodtracker.bptracker.domain.toEntity
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val bloodPressureDao: BloodPressureDao,
    private val bloodSugarDao: BloodSugarDao,
    private val heartRateDao: HeartRateDao,
    private val bmiDao: BmiDao
) {
    suspend fun updateBloodPressure(bloodPressure: BloodPressure) =
        bloodPressureDao.updateBloodPressure(bloodPressure.toEntity())
    suspend fun addBloodPressure(bloodPressure: BloodPressure) =
        bloodPressureDao.addBloodPressure(bloodPressure.toEntity())

    suspend fun getRecentBloodPressure(): List<BloodPressure> =
        bloodPressureDao.getRecentBloodPressure().map { it.toModel() }

    suspend fun addBloodSugar(bloodSugar: BloodSugar) =
        bloodSugarDao.addBloodSugar(bloodSugar.toEntity())
    suspend fun updateBloodSugar(bloodSugar: BloodSugar) =
        bloodSugarDao.updateBloodSugar(bloodSugar.toEntity())

    suspend fun getRecentBloodSugar() = bloodSugarDao.getRecentBloodSugar().map { it.toModel() }
    suspend fun addHeartRate(heartRate: HeartRate) = heartRateDao.addHeartRate(heartRate.toEntity())
    suspend fun updateHeartRate(heartRate: HeartRate) = heartRateDao.updateHeartRate(heartRate.toEntity())
    suspend fun getRecentHeartRate() = heartRateDao.getRecentHeartRate().map { it.toModel() }
    suspend fun addBmi(bmiData: BmiData) = bmiDao.addBmi(bmiData.toEntity())
    suspend fun getAllBP() = bloodPressureDao.getAllData().map { it.toModel() }
    suspend fun getAllBS() = bloodSugarDao.getAllData().map { it.toModel() }
    suspend fun getAllHR() = heartRateDao.getAllData().map { it.toModel() }
    suspend fun getAllBmi() = bmiDao.getAllData().map { it.toModel() }
    suspend fun hasData() = bloodPressureDao.getAllData().isNotEmpty() || bloodSugarDao.getAllData()
        .isNotEmpty() || heartRateDao.getAllData().isNotEmpty() || bmiDao.getAllData().isNotEmpty()

    suspend fun deleteBloodPressure(bloodPressure: BloodPressure) =
        bloodPressureDao.deleteBloodPressure(bloodPressure.toEntity())

    suspend fun deleteBloodSugar(bloodSugar: BloodSugar) =
        bloodSugarDao.deleteBloodSugar(bloodSugar.toEntity())

    suspend fun deleteHeartRate(heartRate: HeartRate) =
        heartRateDao.deleteHeartRate(heartRate.toEntity())

    suspend fun deleteBmi(bmiData: BmiData) = bmiDao.deleteBmi(bmiData.toEntity())

    suspend fun getNewestBloodPressure(context: Context) = try {
        bloodPressureDao.getNewestBloodPressure().toModel()
    } catch (e: Exception) {
        BloodPressure().apply { status = context.getString(R.string.normal_blood_pressure) }
    }

    suspend fun getNewestBloodSugar() = try {
        bloodSugarDao.getNewestBloodSugar().toModel()
    } catch (e: Exception) {
        BloodSugar(sugarConcentration = 5.0)
    }

    suspend fun getNewestHeartRate() = try {
        heartRateDao.getNewestHeartRate().toModel()
    } catch (e: Exception) {
        HeartRate().apply {
            color = "#00C57E"
            status = HeartRateStatus.NORMAL.ordinal
        }
    }

    suspend fun getNewestBmiData() = try {
        bmiDao.getNewestBmiData().toModel()
    } catch (e: Exception) {
        BmiData().apply { bmi = 0.0 }
    }


}