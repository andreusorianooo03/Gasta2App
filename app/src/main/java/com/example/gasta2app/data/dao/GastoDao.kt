package com.example.gasta2app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gasta2app.model.Gasto

@Dao
interface GastoDao {

    @Insert
    suspend fun insertar(gasto: Gasto)

    @Query("SELECT * FROM gastos WHERE gastoConjuntoId = :id")
    fun obtenerGastos(id: Int): LiveData<List<Gasto>>

}