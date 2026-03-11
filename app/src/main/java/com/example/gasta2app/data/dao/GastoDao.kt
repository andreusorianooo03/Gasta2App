package com.example.gasta2app

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GastoDao {

    // Agregar un gasto nuevo
    @Insert
    suspend fun agregarGasto(gasto: Gasto): Long

    // Obtener todos los gastos
    @Query("SELECT * FROM gastos ORDER BY fecha DESC")
    fun obtenerTodos(): LiveData<List<Gasto>>

    // Obtener gastos de hoy
    @Query("""
        SELECT * FROM gastos 
        WHERE fecha >= :inicio AND fecha < :fin
        ORDER BY fecha DESC
    """)
    fun obtenerDeHoy(inicio: Long, fin: Long): LiveData<List<Gasto>>

    // Obtener gastos por rango de fechas
    @Query("""
        SELECT * FROM gastos 
        WHERE fecha >= :inicio AND fecha <= :fin
        ORDER BY fecha DESC
    """)
    fun obtenerPorRango(inicio: Long, fin: Long): LiveData<List<Gasto>>

    // Obtener gastos por categoría
    @Query("""
        SELECT * FROM gastos 
        WHERE categoria = :categoria
        ORDER BY fecha DESC
    """)
    fun obtenerPorCategoria(categoria: String): LiveData<List<Gasto>>

    @Query("""
        SELECT * FROM gastos 
        WHERE esIngreso = 1
        ORDER BY fecha DESC
    """)
    fun obtenerIngresos(): LiveData<List<Gasto>>

    @Query("""
        SELECT * FROM gastos 
        WHERE esIngreso = 0
        ORDER BY fecha DESC
    """)
    fun obtenerGastos(): LiveData<List<Gasto>>

    @Delete
    suspend fun borrarGasto(gasto: Gasto)

    @Update
    suspend fun actualizarGasto(gasto: Gasto)

    @Query("""
        SELECT COALESCE(SUM(cantidad), 0.0) FROM gastos 
        WHERE esIngreso = 0 AND fecha >= :inicio AND fecha <= :fin
    """)
    fun obtenerTotalGastos(inicio: Long, fin: Long): LiveData<Double>

    @Query("""
        SELECT COALESCE(SUM(cantidad), 0.0) FROM gastos 
        WHERE esIngreso = 1 AND fecha >= :inicio AND fecha <= :fin
    """)
    fun obtenerTotalIngresos(inicio: Long, fin: Long): LiveData<Double>
}