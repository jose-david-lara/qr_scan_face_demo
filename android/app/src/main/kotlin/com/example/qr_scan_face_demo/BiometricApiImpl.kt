package com.example.qr_scan_face_demo

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean

class BiometricApiImpl(private val activity: FragmentActivity) : BiometricApi {

    override fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(activity)
        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == BiometricManager.BIOMETRIC_SUCCESS
    }

    override fun authenticate(): Boolean {
        val latch = CountDownLatch(1)
        var result = false

        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(resultInfo: BiometricPrompt.AuthenticationResult) {
                    result = true
                    latch.countDown()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    result = false
                    latch.countDown()
                }

                override fun onAuthenticationFailed() {
                    result = false
                    latch.countDown()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("Inicia sesión con huella o rostro")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(promptInfo)

        latch.await()
        return result
    }

}
