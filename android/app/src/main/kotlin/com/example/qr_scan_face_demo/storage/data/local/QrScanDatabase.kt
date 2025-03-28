package com.example.qr_scan_face_demo.storage.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.qr_scan_face_demo.storage.data.local.entity.QrScanEntity

@Database(entities = [QrScanEntity::class], version = 1, exportSchema = false)
abstract class QrScanDatabase : RoomDatabase() {
    abstract fun qrScanDao(): QrScanDao

    companion object {
        @Volatile
        private var INSTANCE: QrScanDatabase? = null

        fun getInstance(context: Context): QrScanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QrScanDatabase::class.java,
                    "qr_scan_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
