package com.example.qr_scan_face_demo


import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import io.flutter.plugin.common.EventChannel

class MainActivity : FlutterFragmentActivity() {
    private val CHANNEL = "com.example.qr_scan_face_demo/channel"
    private var resultCallback: MethodChannel.Result? = null

    private val qrLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val qrText = data?.getStringExtra(ScanQrActivity.RESULT_QR_CODE)
        resultCallback?.success(qrText ?: "")
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            when (call.method) {
                "nativeFunction" -> {
                    val message = call.argument<String>("message")
                    result.success("Mensaje recibido desde Kotlin: $message")
                }
                "scanQrCode" -> {
                    resultCallback = result
                    val intent = Intent(this, ScanQrActivity::class.java)
                    qrLauncher.launch(intent)
                }
                else -> result.notImplemented()
            }
        }

        TestApi.setUp(flutterEngine.dartExecutor.binaryMessenger, TestApiImpl())
        BiometricApi.setUp(flutterEngine.dartExecutor.binaryMessenger, BiometricApiImpl(this))
        QrHistoryApi.setUp(
            flutterEngine.dartExecutor.binaryMessenger,
            QrHistoryApiImpl(applicationContext)
        )

        EventChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            "com.example.qr_scan_face_demo/scanStream"
        ).setStreamHandler(QrScanStreamHandler(applicationContext))

        PinApi.setUp(flutterEngine.dartExecutor.binaryMessenger, PinApiImpl(applicationContext))
    }
}
