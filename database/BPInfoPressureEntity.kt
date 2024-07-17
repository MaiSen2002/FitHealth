package com.bloodpressure.bloodtracker.bptracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bloodpressure.bloodtracker.bptracker.domain.BPInfoDataModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure

@Entity(tableName = "bp_info_blood_pressure")
data class BPInfoPressureEntity constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var systolic: String = "",
    var diastolic: String = "",
    var color: String = "",
    var status: String = "",
    var textIndicator: String = "",
    var icon: Int = 0,
    var date: String = "",
    var time: String = ""
)

fun BPInfoPressureEntity.toModel(): BPInfoDataModel {
    val entity = this
    return BPInfoDataModel(
        systolic = entity.systolic,
        diastolic = entity.diastolic,
        color = entity.color,
        status = entity.status,
        textIndicator = entity.textIndicator,
        icon = entity.icon,
        id = entity.id,
        date = entity.date,
        time = entity.time
    )
}