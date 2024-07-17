package com.bloodpressure.bloodtracker.bptracker.domain

import com.bloodpressure.bloodtracker.bptracker.database.BloodSugarEntity

data class BloodSugar(
    var id: Int = 0,
    var sugarConcentration: Double = 0.0,
    var measured: Int = -1,
    var date: Long = 0L,
    var time: String = "",
    var note: String = "",
    var isFileTemp: Boolean = false
)

fun BloodSugar.toEntity(): BloodSugarEntity {
    val model = this
    return BloodSugarEntity(
        id = model.id,
        sugarConcentration = model.sugarConcentration,
        measured = model.measured,
        date = model.date,
        time = model.time,
        note = model.note,
        isFileTemp = model.isFileTemp
    )
}
