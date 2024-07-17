package com.bloodpressure.bloodtracker.bptracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure

@Entity(tableName = "blood_pressure")
data class BloodPressureEntity constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var systolic: Int,
    var diastolic: Int,
    var date: Long,
    var time: String,
    var note: String,
    var icon: Int,
    var status: String = "",
    var isFileTemp: Boolean = false,
    var color:String = "",
    var textIndicator:String = ""
)

fun BloodPressureEntity.toModel(): BloodPressure {
    val entity = this
    return BloodPressure(
        systolic = entity.systolic,
        diastolic = entity.diastolic,
        id = entity.id,
        date = entity.date,
        time = entity.time,
        note = entity.note,
        icon = entity.icon,
        status = entity.status,
        isFileTemp = entity.isFileTemp
    )
}