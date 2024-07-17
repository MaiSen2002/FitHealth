package com.bloodpressure.bloodtracker.bptracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRateStatus

@Entity(tableName = "heart_rate")
data class HeartRateEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var heartRate: Int = 0,
    var status: Int = 0,
    var range: String = "",
    var color: String = "",
    var description: String = "",
    var icon: Int = 0,
    var date: Long = 0L,
    var time: String = "",
    var isFileTemp: Boolean = false
)

fun HeartRateEntity.toModel(): HeartRate {
    val entity = this
    return HeartRate(
        id = entity.id,
        heartRate = entity.heartRate,
        status = entity.status,
        range = entity.range,
        color = entity.color,
        description = entity.description,
        icon = entity.icon,
        date = entity.date,
        time = entity.time,
        isFileTemp = entity.isFileTemp
    )
}