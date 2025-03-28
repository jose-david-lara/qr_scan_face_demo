package com.example.qr_scan_face_demo

import android.content.Context
import com.example.qr_scan_face_demo.security.SecurePrefs

class PinApiImpl(private val context: Context) : PinApi {
    override fun savePin(pin: String): Boolean {
        SecurePrefs.savePin(context, pin)
        return true
    }

    override fun getPin(): String? {
        return SecurePrefs.getPin(context)
    }

    override fun clearPin() {
        SecurePrefs.clearPin(context)
    }
}
