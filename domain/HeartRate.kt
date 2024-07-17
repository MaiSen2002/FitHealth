package com.bloodpressure.bloodtracker.bptracker.domain

import com.bloodpressure.bloodtracker.bptracker.database.HeartRateEntity

data class HeartRate(
    var id: Int = 0,
    var heartRate: Int = 80,
    var status: Int = 0,
    var range: String = "",
    var color: String = "",
    var description: String = "",
    var icon: Int = 0,
    var date: Long = 0L,
    var time: String = "",
    var isFileTemp: Boolean = false
)

fun HeartRate.toEntity(): HeartRateEntity {
    val model = this
    return HeartRateEntity(
        id = model.id,
        heartRate = model.heartRate,
        status = status,
        range = model.range,
        color = model.color,
        description = model.description,
        icon = model.icon,
        date = model.date,
        time = model.time,
        isFileTemp = model.isFileTemp
    )
}