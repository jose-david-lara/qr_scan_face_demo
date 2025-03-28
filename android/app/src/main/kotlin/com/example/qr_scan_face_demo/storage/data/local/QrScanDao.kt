package com.example.qr_scan_face_demo.storage.data.local

import androidx.room.*
import com.example.qr_scan_face_demo.storage.data.local.entity.QrScanEntity

@Dao
interface QrScanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scan: QrScanEntity)

    @Query("SELECT * FROM qr_scans ORDER BY timestamp DESC")
    fun getAll(): List<QrScanEntity>

    @Query("SELECT * FROM qr_scans ORDER BY timestamp DESC")
    fun observeAll(): kotlinx.coroutines.flow.Flow<List<QrScanEntity>>

}
