package com.example.gasta2app

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GastoCompartidoDao {


    @Insert
    suspend fun agregarGasto(gasto: GastoCompartido): Long

    @Query("SELECT * FROM gastos_compartidos ORDER BY fecha DESC")
    fun obtenerTodos(): LiveData<List<GastoCompartido>>

    @Query("SELECT * FROM gastos_compartidos WHERE id = :gastoId")
    fun obtenerPorId(gastoId: Int): LiveData<GastoCompartido>

    @Query("""
        SELECT * FROM gastos_compartidos 
        WHERE participantes LIKE '%' || :persona || '%'
        ORDER BY fecha DESC
    """)
    fun obtenerPorPersona(persona: String): LiveData<List<GastoCompartido>>

    @Query("SELECT COALESCE(SUM(cantidad), 0.0) FROM gastos_compartidos")
    fun obtenerTotal(): LiveData<Double>

    @Update
    suspend fun actualizarGasto(gasto: GastoCompartido)

    @Delete
    suspend fun borrarGasto(gasto: GastoCompartido)
}