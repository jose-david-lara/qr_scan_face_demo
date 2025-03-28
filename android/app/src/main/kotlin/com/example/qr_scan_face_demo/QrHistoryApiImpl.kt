package com.example.qr_scan_face_demo

import android.content.Context
import com.example.qr_scan_face_demo.storage.data.local.QrScanDatabase
import com.example.qr_scan_face_demo.QrHistoryApi
import com.example.qr_scan_face_demo.QrScanResult

class QrHistoryApiImpl(private val context: Context) : QrHistoryApi {
    override fun getAllScans(): List<QrScanResult> {
        val scans: List<com.example.qr_scan_face_demo.storage.data.local.entity.QrScanEntity> =
            QrScanDatabase.getInstance(context).qrScanDao().getAll()

        return scans.map { entity ->
            QrScanResult(entity.content, entity.timestamp)
        }
    }
}
