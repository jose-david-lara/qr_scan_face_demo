package com.example.qr_scan_face_demo.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecurePrefs {
    private const val PREF_NAME = "secure_prefs"
    private const val PIN_KEY = "user_pin"

    private fun prefs(context: Context) =
        EncryptedSharedPreferences.create(
            PREF_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun savePin(context: Context, pin: String) {
        prefs(context).edit().putString(PIN_KEY, pin).apply()
    }

    fun getPin(context: Context): String? {
        return prefs(context).getString(PIN_KEY, null)
    }

    fun clearPin(context: Context) {
        prefs(context).edit().remove(PIN_KEY).apply()
    }
}
