package com.bloodpressure.bloodtracker.bptracker.domain

import com.bloodpressure.bloodtracker.bptracker.database.BmiEntity

data class BmiData(
    var id: Int = 0,
    var height: Int = 0,
    var weight: Int = 0,
    var bmi: Double = 0.0,
    // true: male, false: female
    var gender: Boolean = false,
    var status: String = "",
    var date: Long = 0L,
    var time: String = "",
    var description: String = "",
    var isFileTemp: Boolean = false
)

fun BmiData.toEntity(): BmiEntity {
    val entity = this
    return BmiEntity(
        id = entity.id,
        height = entity.height,
        weight = entity.weight,
        bmi = entity.bmi,
        gender = entity.gender,
        status = entity.status,
        date = entity.date,
        time = entity.time,
        description = entity.description,
        isFileTemp = entity.isFileTemp
    )
}
