package com.bloodpressure.bloodtracker.bptracker.domain

data class BPInfoDataModel(
    var systolic: String = "",
    var diastolic: String = "",
    var color: String = "",
    var status: String = "",
    var textIndicator: String = "",
    var icon: Int = 0,
    var id: Int = 0,
    var date: String = "0L",
    var time: String = ""
)