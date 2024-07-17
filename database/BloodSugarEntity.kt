package com.bloodpressure.bloodtracker.bptracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugar

@Entity(tableName = "blood_sugar")
data class BloodSugarEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var sugarConcentration: Double = 0.0,
    var measured: Int = 0,
    var date: Long = 0L,
    var time: String = "",
    var note: String = "",
    var isFileTemp: Boolean = false
)

fun BloodSugarEntity.toModel(): BloodSugar {
    val entity = this
    return BloodSugar(
        id = entity.id,
        sugarConcentration = entity.sugarConcentration,
        measured = entity.measured,
        date = entity.date,
        time = entity.time,
        note = entity.note,
        isFileTemp = entity.isFileTemp
    )
}
