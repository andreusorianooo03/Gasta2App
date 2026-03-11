package com.example.gasta2app.data.local

import androidx.room.*
import com.example.gasta2app.model.Deuda

@Dao
interface DeudaDao {

    @Insert
    suspend fun insertar(deuda: Deuda)

    @Query("SELECT * FROM deudas")
    suspend fun obtenerTodas(): List<Deuda>

    @Delete
    suspend fun eliminar(deuda: Deuda)

    @Update
    suspend fun actualizar(deuda: Deuda)
}