package com.example.gasta2app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gasta2app.data.dao.DeudaDao
import com.example.gasta2app.data.dao.MovimientoDao
import com.example.gasta2app.data.dao.GastoConjuntoDao
import com.example.gasta2app.data.dao.ParticipanteDao
import com.example.gasta2app.data.dao.GastoDao
import com.example.gasta2app.model.Movimiento
import com.example.gasta2app.model.Deuda
import com.example.gasta2app.model.GastoConjunto
import com.example.gasta2app.model.Participante
import com.example.gasta2app.model.Gasto

@Database(
    entities = [
        Movimiento::class,
        Deuda::class,
        GastoConjunto::class,
        Participante::class,
        Gasto::class
    ],
    version = 4
)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun movimientoDao(): MovimientoDao
    abstract fun deudaDao(): DeudaDao

    abstract fun gastoConjuntoDao(): GastoConjuntoDao
    abstract fun participanteDao(): ParticipanteDao
    abstract fun gastoDao(): GastoDao

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