package com.example.gasta2app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gasta2app.model.Deuda

@Dao
interface DeudaDao {

    @Insert
    suspend fun insertar(deuda: Deuda)

    @Delete
    suspend fun eliminar(deuda: Deuda)

    @Query("SELECT * FROM deudas")
    fun obtenerTodas(): LiveData<List<Deuda>>

}