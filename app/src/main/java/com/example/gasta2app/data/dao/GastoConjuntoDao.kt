package com.example.gasta2app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gasta2app.model.GastoConjunto

@Dao
interface GastoConjuntoDao {

    @Insert
    suspend fun insertar(gastoConjunto: GastoConjunto): Long

    @Query("SELECT * FROM gastos_conjuntos")
    fun obtenerTodos(): LiveData<List<GastoConjunto>>

}