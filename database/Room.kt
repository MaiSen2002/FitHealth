package com.bloodpressure.bloodtracker.bptracker.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update

@Dao
interface BloodPressureDao {
    @Insert
    suspend fun addBloodPressure(entity: BloodPressureEntity)
    @Update
    suspend fun updateBloodPressure(entity: BloodPressureEntity)

    @Query(
        """
        SELECT *
        FROM blood_pressure
        ORDER BY id DESC
        LIMIT 3
    """
    )
    suspend fun getRecentBloodPressure(): List<BloodPressureEntity>

    @Query(
        """
        SELECT *
        FROM blood_pressure
        ORDER BY id DESC
        LIMIT 1
    """
    )
    suspend fun getNewestBloodPressure(): BloodPressureEntity

    @Query(
        """
        SELECT * FROM blood_pressure
    """
    )
    suspend fun getAllData(): List<BloodPressureEntity>

    @Delete
    suspend fun deleteBloodPressure(bloodPressureEntity: BloodPressureEntity)

}

@Dao
interface BloodSugarDao {
    @Insert
    suspend fun addBloodSugar(entity: BloodSugarEntity)
    @Update
    suspend fun updateBloodSugar(entity: BloodSugarEntity)

    @Query(
        """
        SELECT *
        FROM blood_sugar
        ORDER BY id DESC
        LIMIT 3
    """
    )
    suspend fun getRecentBloodSugar(): List<BloodSugarEntity>

    @Query(
        """
        SELECT *
        FROM blood_sugar
        ORDER BY id DESC
        LIMIT 1
    """
    )
    suspend fun getNewestBloodSugar(): BloodSugarEntity

    @Query(
        """
        SELECT * FROM blood_sugar
    """
    )
    suspend fun getAllData(): List<BloodSugarEntity>

    @Delete
    suspend fun deleteBloodSugar(bloodSugarEntity: BloodSugarEntity)

}

@Dao
interface HeartRateDao {
    @Insert
    suspend fun addHeartRate(entity: HeartRateEntity)
    @Update
    suspend fun updateHeartRate(entity: HeartRateEntity)

    @Query(
        """
        SELECT *
        FROM heart_rate
        ORDER BY id DESC
        LIMIT 3
    """
    )
    suspend fun getRecentHeartRate(): List<HeartRateEntity>


    @Query(
        """
        SELECT *
        FROM heart_rate
        ORDER BY id DESC
        LIMIT 1
    """
    )
    suspend fun getNewestHeartRate(): HeartRateEntity

    @Query(
        """
        SELECT * FROM heart_rate
    """
    )
    suspend fun getAllData(): List<HeartRateEntity>

    @Delete
    suspend fun deleteHeartRate(heartRateEntity: HeartRateEntity)
}

@Dao
interface BmiDao {
    @Insert
    suspend fun addBmi(bmiEntity: BmiEntity)

    @Query(
        """
        SELECT * FROM bmi
    """
    )
    suspend fun getAllData(): List<BmiEntity>

    @Query(
        """
        SELECT *
        FROM bmi
        ORDER BY id DESC
        LIMIT 1
    """
    )
    suspend fun getNewestBmiData(): BmiEntity

    @Delete
    suspend fun deleteBmi(bmiEntity: BmiEntity)
}

@Database(
    entities = [BPInfoPressureEntity::class,BloodPressureEntity::class, BloodSugarEntity::class, HeartRateEntity::class, BmiEntity::class],
    version = 3,
    exportSchema = false
)
abstract class BloodPressureDatabase : RoomDatabase() {
    abstract val bloodPressureDao: BloodPressureDao
    abstract val bloodSugarDao: BloodSugarDao
    abstract val heartRateDao: HeartRateDao
    abstract val bmiDao: BmiDao
}