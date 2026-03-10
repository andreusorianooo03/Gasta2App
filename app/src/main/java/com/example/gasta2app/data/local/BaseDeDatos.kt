package com.example.gasta2app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gasta2app.data.dao.MovimientoDao
import com.example.gasta2app.model.Movimiento

@Database(entities = [Movimiento::class], version = 1)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun movimientoDao(): MovimientoDao

    companion object {

        @Volatile
        private var INSTANCE: BaseDeDatos? = null

        fun obtenerBaseDeDatos(context: Context): BaseDeDatos {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "gasta2_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}