package com.example.gasta2app

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ParticipanteGastoDao {

    @Insert
    suspend fun agregarParticipante(participante: ParticipanteGasto): Long

    @Query("""
        SELECT * FROM participantes_gasto 
        WHERE gastoCompartidoId = :gastoCompartidoId
    """)
    fun obtenerParticipantes(gastoCompartidoId: Int): LiveData<List<ParticipanteGasto>>

    @Query("""
        SELECT COALESCE(SUM(cantidadAPagar), 0.0) FROM participantes_gasto 
        WHERE nombrePersona = :persona
    """)
    fun obtenerTotalADeber(persona: String): LiveData<Double>

    @Query("""
        SELECT cantidadAPagar FROM participantes_gasto 
        WHERE gastoCompartidoId = :gastoCompartidoId AND nombrePersona = :persona
    """)
    fun obtenerCantidadAPagar(gastoCompartidoId: Int, persona: String): LiveData<Double>

    @Delete
    suspend fun borrarParticipante(participante: ParticipanteGasto)

    @Query("DELETE FROM participantes_gasto WHERE gastoCompartidoId = :gastoCompartidoId")
    suspend fun borrarParticipantesPorGasto(gastoCompartidoId: Int)
}