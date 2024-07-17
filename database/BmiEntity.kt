package com.bloodpressure.bloodtracker.bptracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bloodpressure.bloodtracker.bptracker.domain.BmiData

@Entity(tableName = "bmi")
data class BmiEntity(
    @PrimaryKey(autoGenerate = true)
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

fun BmiEntity.toModel(): BmiData {
    val entity = this
    return BmiData(
        id = entity.id,
        height = entity.height,
        weight = entity.weight,
        bmi = entity.bmi,
        gender = entity.gender,
        status = entity.status,
        date = entity.date,
        time = entity.time,
        description = entity.time,
        isFileTemp = entity.isFileTemp
    )
}
