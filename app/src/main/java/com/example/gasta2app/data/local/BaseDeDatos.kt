package com.example.gasta2app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gasta2app.data.dao.MovimientoDao
import com.example.gasta2app.model.Movimiento
import com.example.gasta2app.model.Deuda

@Database(
    entities = [Movimiento::class, Deuda::class],
    version = 3
)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun movimientoDao(): MovimientoDao
    abstract fun deudaDao(): DeudaDao

    companion object {

        @Volatile
        private var INSTANCE: BaseDeDatos? = null

        fun obtenerBaseDeDatos(context: Context): BaseDeDatos {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "gasta2_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}