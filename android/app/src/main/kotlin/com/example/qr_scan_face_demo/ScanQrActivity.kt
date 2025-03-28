package com.example.qr_scan_face_demo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import android.view.Surface
import android.view.SurfaceView
import android.widget.FrameLayout
import android.view.ViewGroup
import android.media.Image

import com.example.qr_scan_face_demo.storage.data.local.QrScanDatabase
import com.example.qr_scan_face_demo.storage.data.local.entity.QrScanEntity
import androidx.camera.view.PreviewView
import android.widget.Button
import android.view.View

class ScanQrActivity : AppCompatActivity() {

    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private lateinit var previewView: PreviewView
    private lateinit var container: FrameLayout

    companion object {
        const val RESULT_QR_CODE = "qr_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)

        previewView = findViewById(R.id.preview_view)

        val btnClose: Button = findViewById(R.id.btn_close)
        btnClose.setOnClickListener {
            finish() // Cierra el escáner manualmente
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()

            preview.setSurfaceProvider(previewView.surfaceProvider) // ✅ correcto con PreviewView

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                processImage(imageProxy)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun processImage(imageProxy: ImageProxy) {
        val mediaImage: Image? = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.rawValue?.let {
                            sendResultAndFinish(it)
                        }
                    }
                }
                .addOnFailureListener {
                    // fall silently
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    private fun sendResultAndFinish(result: String) {
        // Guardamos en Room
        val dao = QrScanDatabase.getInstance(applicationContext).qrScanDao()
        Thread {
            dao.insert(QrScanEntity(content = result))
        }.start()

        // Devolvemos el resultado a Flutter
        val data = Intent().putExtra(RESULT_QR_CODE, result)
        setResult(RESULT_OK, data)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
