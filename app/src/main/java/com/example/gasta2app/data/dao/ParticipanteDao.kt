package com.example.gasta2app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gasta2app.model.Participante

@Dao
interface ParticipanteDao {

    @Insert
    suspend fun insertar(participante: Participante)

    @Query("SELECT * FROM participantes WHERE gastoConjuntoId = :id")
    fun obtenerParticipantes(id: Int): LiveData<List<Participante>>

}