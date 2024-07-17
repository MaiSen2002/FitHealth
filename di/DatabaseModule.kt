package com.bloodpressure.bloodtracker.bptracker.di

import android.content.Context
import androidx.room.Room
import com.bloodpressure.bloodtracker.bptracker.database.BloodPressureDao
import com.bloodpressure.bloodtracker.bptracker.database.BloodPressureDatabase
import com.bloodpressure.bloodtracker.bptracker.database.BloodSugarDao
import com.bloodpressure.bloodtracker.bptracker.database.BmiDao
import com.bloodpressure.bloodtracker.bptracker.database.HeartRateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): BloodPressureDatabase {
        return Room.databaseBuilder(
            appContext,
            BloodPressureDatabase::class.java,
            "blood_pressure"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideBloodPressureDao(bloodPressureDatabase: BloodPressureDatabase): BloodPressureDao {
        return bloodPressureDatabase.bloodPressureDao
    }

    @Provides
    fun provideBloodSugarDao(bloodPressureDatabase: BloodPressureDatabase): BloodSugarDao {
        return bloodPressureDatabase.bloodSugarDao
    }

    @Provides
    fun provideHeartRateDao(bloodPressureDatabase: BloodPressureDatabase): HeartRateDao {
        return bloodPressureDatabase.heartRateDao
    }

    @Provides
    fun provideBmiDao(bloodPressureDatabase: BloodPressureDatabase): BmiDao {
        return bloodPressureDatabase.bmiDao
    }

}