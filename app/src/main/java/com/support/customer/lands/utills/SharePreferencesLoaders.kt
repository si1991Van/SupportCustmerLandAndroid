package com.support.customer.lands.utills

import android.content.Context
import android.content.SharedPreferences

object SharePreferencesLoaders {
    private var sharedPreferences: SharedPreferences? = null
    private var instance: SharePreferencesLoaders? = null
    private val PREFERENCES_NAME = "PREFERENCES_NAME"



    fun init(pContext: Context): SharedPreferences {
        sharedPreferences = pContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences as SharedPreferences
    }


    fun saveValueToSharedPreferences(): SharedPreferences.Editor? {
        return sharedPreferences?.edit()
    }

    fun saveValue(pKeyName: String, pValue: String?) {
        sharedPreferences?.edit()?.putString(pKeyName, pValue)?.apply()
    }

    fun saveValue(pKeyName: String, pValue: Int) {
        sharedPreferences?.edit()?.putInt(pKeyName, pValue)?.apply()
    }

    fun saveValue(pKeyName: String, pValue: Long) {
        sharedPreferences?.edit()?.putLong(pKeyName, pValue)?.apply()
    }

    fun saveValue(pKeyName: String, pValue: Boolean) {
        sharedPreferences?.edit()?.putBoolean(pKeyName, pValue)?.apply()
    }

    fun getValue(pKeyName: String, pDefaultValue: String): String? {
        return sharedPreferences?.getString(pKeyName, pDefaultValue)
    }

    fun getValue(accesS_TOKEN: String, nothing: Nothing?): String? {
        return sharedPreferences?.getString(accesS_TOKEN, nothing)
    }

    fun getValue(pKeyName: String, pDefaultValue: Int): Int? {
        return sharedPreferences?.getInt(pKeyName, pDefaultValue)
    }

    fun getValue(pKeyName: String, pDefaultValue: Long): Long? {
        return sharedPreferences?.getLong(pKeyName, pDefaultValue)
    }

    fun clear()  {
        sharedPreferences?.edit()?.clear()?.apply()
    }


    fun onDestroy() {
        sharedPreferences = null
        instance = null
    }

    fun getValue(pKeyName: String, pDefaultValue: Boolean): Boolean? {
        return sharedPreferences?.getBoolean(pKeyName, pDefaultValue)
    }
}