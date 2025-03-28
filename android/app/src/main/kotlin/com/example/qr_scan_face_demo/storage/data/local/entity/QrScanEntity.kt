package com.example.qr_scan_face_demo.storage.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_scans")
data class QrScanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
