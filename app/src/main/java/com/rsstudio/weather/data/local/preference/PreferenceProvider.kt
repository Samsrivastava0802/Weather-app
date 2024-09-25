package com.rsstudio.weather.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceProvider
@Inject
constructor(
    @ApplicationContext private val context: Context
){
    companion object {
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val ADDRESS = "address"
        const val LAST_REFRESHED_TIME = "last_refreshed_time"

        // default value
        const val DFT_LATITUDE : Float = 26.122000F
        const val DFT_LONGITUDE : Float = 85.379303F
        const val DFT_REFRESHED_TIME : Long = 0
        const val DFT_ADDRESS : String = "Sherpur Muzaffarpur, Bihar"

    }
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)
    fun clearPreferences() {
        preference.edit().clear().apply()
    }
    fun saveAddress(address: String) {
        preference.edit().putString(
            ADDRESS,
            address
        ).apply()
    }
    fun getAddress(): String? {
        return preference.getString(ADDRESS, DFT_ADDRESS)
    }
    fun saveLatitude(latitude: Float) {
        preference.edit().putFloat(
            LATITUDE,
            latitude
        ).apply()
    }
    fun getLatitude(): Float {
        return preference.getFloat(LATITUDE, DFT_LATITUDE)
    }
    fun saveLongitude(longitude: Float) {
        preference.edit().putFloat(
            LONGITUDE,
            longitude
        ).apply()
    }

    fun getLongitude(): Float {
        return preference.getFloat(LONGITUDE, DFT_LONGITUDE)
    }

    fun saveLastRefreshedTime(timeStamp: Long) {
        preference.edit().putLong(
            LAST_REFRESHED_TIME,
            timeStamp
        ).apply()
    }

    fun getLastRefreshedTime(): Long {
        return preference.getLong(LAST_REFRESHED_TIME, DFT_REFRESHED_TIME)
    }


}