package com.example.gasta2app.data.repository

import androidx.lifecycle.LiveData
import com.example.gasta2app.data.dao.GastoConjuntoDao
import com.example.gasta2app.data.dao.GastoDao
import com.example.gasta2app.data.dao.ParticipanteDao
import com.example.gasta2app.model.Gasto
import com.example.gasta2app.model.GastoConjunto
import com.example.gasta2app.model.Participante

class GastoConjuntoRepository(
    private val gastoConjuntoDao: GastoConjuntoDao,
    private val participanteDao: ParticipanteDao,
    private val gastoDao: GastoDao
) {
    val listaGrupos: LiveData<List<GastoConjunto>> = gastoConjuntoDao.obtenerTodos()

    fun obtenerParticipantes(grupoId: Int): LiveData<List<Participante>> {
        return participanteDao.obtenerParticipantes(grupoId)
    }

    fun obtenerGastos(grupoId: Int): LiveData<List<Gasto>> {
        return gastoDao.obtenerGastos(grupoId)
    }

    suspend fun crearGrupo(nombre: String, participantes: List<String>) {
        val grupoId = gastoConjuntoDao.insertar(GastoConjunto(nombre = nombre)).toInt()
        participantes.forEach { participante ->
            participanteDao.insertar(
                Participante(
                    nombre = participante,
                    gastoConjuntoId = grupoId
                )
            )
        }
    }

    suspend fun insertarGasto(
        grupoId: Int,
        descripcion: String,
        cantidad: Double,
        pagador: String
    ) {
        gastoDao.insertar(
            Gasto(
                descripcion = descripcion,
                cantidad = cantidad,
                pagadoPor = pagador,
                gastoConjuntoId = grupoId
            )
        )
    }
}
