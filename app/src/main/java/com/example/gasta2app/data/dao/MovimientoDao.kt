package com.example.gasta2app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gasta2app.model.Movimiento

@Dao
interface MovimientoDao {

    @Insert
    suspend fun insertarMovimiento(movimiento: Movimiento)

    @Delete
    suspend fun eliminarMovimiento(movimiento: Movimiento)

    @Query("SELECT * FROM movimientos ORDER BY id DESC")
    fun obtenerMovimientos(): LiveData<List<Movimiento>>
}