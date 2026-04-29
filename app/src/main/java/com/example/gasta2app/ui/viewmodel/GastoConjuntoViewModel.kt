package com.example.gasta2app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gasta2app.data.repository.GastoConjuntoRepository
import com.example.gasta2app.model.Gasto
import com.example.gasta2app.model.GastoConjunto
import com.example.gasta2app.model.Participante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GastoConjuntoViewModel(
    private val repository: GastoConjuntoRepository
) : ViewModel() {
    val listaGrupos: LiveData<List<GastoConjunto>> = repository.listaGrupos

    private val _mensajeUi = MutableStateFlow<String?>(null)
    val mensajeUi: StateFlow<String?> = _mensajeUi.asStateFlow()

    fun limpiarMensajeUi() {
        _mensajeUi.value = null
    }

    fun obtenerParticipantes(grupoId: Int): LiveData<List<Participante>> {
        return repository.obtenerParticipantes(grupoId)
    }

    fun obtenerGastos(grupoId: Int): LiveData<List<Gasto>> {
        return repository.obtenerGastos(grupoId)
    }

    fun crearGrupo(nombre: String, participantes: List<String>) {
        val nombreLimpio = nombre.trim()
        val participantesLimpios = participantes.map { it.trim() }.filter { it.isNotBlank() }

        if (nombreLimpio.isBlank()) {
            _mensajeUi.value = "El nombre del grupo no puede estar vacio."
            return
        }
        if (participantesLimpios.isEmpty()) {
            _mensajeUi.value = "Agrega al menos un participante."
            return
        }

        viewModelScope.launch {
            try {
                repository.crearGrupo(nombreLimpio, participantesLimpios)
                _mensajeUi.value = "Grupo creado correctamente."
            } catch (_: Exception) {
                _mensajeUi.value = "No se pudo guardar el grupo."
            }
        }
    }

    fun agregarGasto(
        grupoId: Int,
        descripcion: String,
        cantidadTexto: String,
        pagador: String
    ) {
        val descripcionLimpia = descripcion.trim()
        val pagadorLimpio = pagador.trim()
        val cantidad = cantidadTexto.trim().replace(",", ".").toDoubleOrNull()

        if (descripcionLimpia.isBlank()) {
            _mensajeUi.value = "La descripcion es obligatoria."
            return
        }
        if (pagadorLimpio.isBlank()) {
            _mensajeUi.value = "Selecciona quien pago el gasto."
            return
        }
        if (cantidad == null || cantidad <= 0.0) {
            _mensajeUi.value = "La cantidad debe ser un numero mayor que cero."
            return
        }

        viewModelScope.launch {
            try {
                repository.insertarGasto(
                    grupoId = grupoId,
                    descripcion = descripcionLimpia,
                    cantidad = cantidad,
                    pagador = pagadorLimpio
                )
                _mensajeUi.value = "Gasto guardado correctamente."
            } catch (_: Exception) {
                _mensajeUi.value = "No se pudo guardar el gasto."
            }
        }
    }
}
