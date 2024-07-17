package com.bloodpressure.bloodtracker.bptracker.domain

data class BloodDataModel(
    var id: Int = 0,
    var date: Long = 0L,
    var time: String = "",
    var type: BloodType = BloodType.BLOOD_PRESSURE,
    var weekdays: String = "",
    var isFileTemp: Boolean = false,
    var isAds: Int = -1
)