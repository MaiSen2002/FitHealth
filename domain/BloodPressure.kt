package com.bloodpressure.bloodtracker.bptracker.domain

import com.bloodpressure.bloodtracker.bptracker.database.BloodPressureEntity

data class BloodPressure(
    var systolic: Int = 100,
    var diastolic: Int = 60,
    var id: Int = 0,
    var date: Long = 0L,
    var time: String = "",
    var note: String = "",
    var icon: Int = 0,
    var status: String = "",
    var isFileTemp: Boolean = false
)

fun BloodPressure.toEntity(): BloodPressureEntity {
    val model = this
    return BloodPressureEntity(
        id = model.id,
        systolic = model.systolic,
        diastolic = model.diastolic,
        date = model.date,
        time = model.time,
        note = model.note,
        icon = model.icon,
        status = model.status,
        isFileTemp = model.isFileTemp
    )
}