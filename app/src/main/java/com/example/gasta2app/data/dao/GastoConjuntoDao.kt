package com.example.gasta2app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gasta2app.model.GastoConjunto

@Dao
interface GastoConjuntoDao {

    @Insert
    suspend fun insertar(gastoConjunto: GastoConjunto)

    @Query("SELECT * FROM gastos_conjuntos")
    fun obtenerTodos(): LiveData<List<GastoConjunto>>

    @Query("SELECT MAX(id) FROM gastos_conjuntos")
    suspend fun obtenerUltimoId(): Int

}