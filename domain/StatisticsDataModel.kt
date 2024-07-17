package com.bloodpressure.bloodtracker.bptracker.domain

data class StatisticsDataModel(
    var type: Int = 0,
    var bloodPressures: List<BloodPressure> = arrayListOf(),
    var bloodSugars: List<BloodSugar> = arrayListOf(),
    var heartRates: List<HeartRate> = arrayListOf(),
    var listBmi: List<BmiData> = arrayListOf()
)
