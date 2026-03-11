package com.example.gasta2app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gasta2app.Deuda

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