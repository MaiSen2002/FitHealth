package com.bloodpressure.bloodtracker.bptracker.helper

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface SharedPreferenceHelper {
    fun setString(key: String, value: String)
    fun getString(key: String): String?
    fun setInt(key: String, value: Int)
    fun getInt(key: String): Int?

    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean?

}

@Singleton
class PreferenceHelper @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferenceHelper {
    companion object {
        const val APP_PREFS = "app_prefs"
        const val PREF_CURRENT_LANGUAGE = "pref_current_language"
        const val PREF_SHOWED_START_LANGUAGE = "pref_showed_start_language"
    }

    private var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            APP_PREFS,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun setString(key: String, value: String) {
        sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun setInt(key: String, value: Int) {
        sharedPreferences
            .edit()
            .putInt(key, value)
            .apply()
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    override fun setBoolean(key: String, value: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(key, value)
            .apply()
    }

    override fun getBoolean(key: String): Boolean? {
        return sharedPreferences.getBoolean(key, false)
    }


    fun setAdsRemoteConfig(checkStatus: Boolean, keyAds: String) {
        sharedPreferences.edit().putBoolean(keyAds, checkStatus).apply()
    }

    fun getAdsRemoteConfig(keyAds: String): Boolean {
        return sharedPreferences.getBoolean(keyAds, true)
    }
}