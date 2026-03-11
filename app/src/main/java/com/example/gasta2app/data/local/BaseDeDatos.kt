package com.example.gasta2app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gasta2app.data.dao.DeudaDao

@Database(
    entities = [
        Gasto::class,
        Deuda::class,
        GastoCompartido::class,
        ParticipanteGasto::class
    ],
    version = 1
)
abstract class BaseDatos : RoomDatabase() {

    abstract fun gastoDao(): GastoDao
    abstract fun deudaDao(): DeudaDao
    abstract fun gastoCompartidoDao(): GastoCompartidoDao
    abstract fun participanteGastoDao(): ParticipanteGastoDao

    companion object {
        @Volatile
        private var instancia: BaseDatos? = null

        fun obtener(contexto: Context): BaseDatos {
            if (instancia != null) {
                return instancia!!
            }

            synchronized(this) {
                val nuevaInstancia = Room.databaseBuilder(
                    contexto.applicationContext,
                    BaseDatos::class.java,
                    "gasta2_db"
                ).build()

                instancia = nuevaInstancia
                return nuevaInstancia
            }
        }
    }
}