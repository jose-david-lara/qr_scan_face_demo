package com.example.qr_scan_face_demo

import android.content.Context
import com.example.qr_scan_face_demo.storage.data.local.QrScanDatabase
import com.example.qr_scan_face_demo.QrScanResult
import io.flutter.plugin.common.EventChannel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class QrScanStreamHandler(private val context: Context) : EventChannel.StreamHandler {
    private var job: Job? = null

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        val dao = QrScanDatabase.getInstance(context).qrScanDao()

        job = CoroutineScope(Dispatchers.IO).launch {
            dao.observeAll()
                .map { list -> list.map { QrScanResult(it.content, it.timestamp) } }
                .collectLatest { scanResults ->
                    events?.success(scanResults)
                }
        }
    }

    override fun onCancel(arguments: Any?) {
        job?.cancel()
        job = null
    }
}
