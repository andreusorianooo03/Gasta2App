package com.example.gasta2app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GastosCompartidosViewModel(aplicacion: Application) : AndroidViewModel(aplicacion) {

    private val bd = BaseDatos.obtener(aplicacion)
    private val repositorio = RepositorioGastosCompartidos(
        bd.gastoCompartidoDao(),
        bd.participanteGastoDao()
    )

    val gastosActuales: LiveData<List<GastoCompartido>> = repositorio.todosLosGastos

    private val _gastoSeleccionado = MutableLiveData<Int>()

    val gastoActual: LiveData<GastoCompartido> = _gastoSeleccionado.switchMap { gastoId ->
        bd.gastoCompartidoDao().obtenerPorId(gastoId)
    }

    val participantesDelGasto: LiveData<List<ParticipanteGasto>> = _gastoSeleccionado.switchMap { gastoId ->
        repositorio.obtenerParticipantes(gastoId)
    }

    val deudasDelGasto: LiveData<List<Deuda>> = _gastoSeleccionado.switchMap { gastoId ->
        repositorio.calcularDeudas(gastoId)
    }

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    private val _gastoCreado = MutableLiveData<Boolean>()
    val gastoCreado: LiveData<Boolean> = _gastoCreado

    fun crearGastoCompartido(
        nombre: String,
        quienPago: String,
        cantidad: String,
        descripcion: String,
        personas: List<String>
    ) {
        viewModelScope.launch {
            try {
                val cantidadDouble = cantidad.toDoubleOrNull() ?: throw Exception("Cantidad inválida")
                repositorio.crearGastoCompartido(nombre, quienPago, cantidadDouble, descripcion, personas)
                _mensaje.value = "Gasto compartido creado"
                _gastoCreado.value = true
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
                _gastoCreado.value = false
            }
        }
    }

    fun seleccionarGasto(gastoId: Int) {
        _gastoSeleccionado.value = gastoId
    }

    fun obtenerGastosPorPersona(persona: String): LiveData<List<GastoCompartido>> {
        return repositorio.obtenerGastosPorPersona(persona)
    }

    fun eliminarGasto(gasto: GastoCompartido) {
        viewModelScope.launch {
            try {
                repositorio.borrarGasto(gasto)
                _mensaje.value = "Gasto eliminado"
            } catch (e: Exception) {
                _mensaje.value = "Error al eliminar: ${e.message}"
            }
        }
    }

    // Función especial: obtener resumen de deudas simplificadas
    fun obtenerResumenDeudas(): LiveData<Map<String, Double>> {
        return deudasDelGasto.switchMap { deudas ->
            val resumen = mutableMapOf<String, Double>()

            // Agrupar deudas por deudor
            for (deuda in deudas) {
                val clave = "${deuda.deudor} → ${deuda.acreedor}"
                val cantidadActual = resumen[clave] ?: 0.0
                resumen[clave] = cantidadActual + deuda.cantidad
            }

            MutableLiveData(resumen)
        }
    }
}