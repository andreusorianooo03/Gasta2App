package com.example.gasta2app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap

class RepositorioGastosCompartidos(
    private val gastoCompartidoDao: GastoCompartidoDao,
    private val participanteGastoDao: ParticipanteGastoDao
) {

    val todosLosGastos: LiveData<List<GastoCompartido>> = gastoCompartidoDao.obtenerTodos()

    suspend fun crearGastoCompartido(
        nombre: String,
        quienPago: String,
        cantidad: Double,
        descripcion: String,
        personas: List<String>
    ) {
        // Validaciones
        if (nombre.isBlank()) {
            throw Exception("El nombre del gasto no puede estar vacío")
        }
        if (cantidad <= 0) {
            throw Exception("La cantidad debe ser mayor a 0")
        }
        if (quienPago.isBlank()) {
            throw Exception("Debe indicar quién pagó")
        }
        if (personas.isEmpty()) {
            throw Exception("Debe haber al menos un participante")
        }
        if (!personas.contains(quienPago)) {
            throw Exception("El que pagó debe estar en los participantes")
        }

        // Crear el gasto compartido
        val participantesTexto = personas.joinToString(",")
        val gasto = GastoCompartido(
            nombre = nombre,
            quienPago = quienPago,
            cantidad = cantidad,
            descripcion = descripcion,
            fecha = System.currentTimeMillis(),
            participantes = participantesTexto
        )

        val gastoId = gastoCompartidoDao.agregarGasto(gasto).toInt()

        // Calcular cuánto paga cada persona
        val cantidadPorPersona = cantidad / personas.size

        // Agregar a cada participante
        for (persona in personas) {
            val participante = ParticipanteGasto(
                gastoCompartidoId = gastoId,
                nombrePersona = persona,
                cantidadAPagar = cantidadPorPersona
            )
            participanteGastoDao.agregarParticipante(participante)
        }
    }

    fun obtenerGastosPorPersona(persona: String): LiveData<List<GastoCompartido>> {
        return gastoCompartidoDao.obtenerPorPersona(persona)
    }

    fun obtenerParticipantes(gastoCompartidoId: Int): LiveData<List<ParticipanteGasto>> {
        return participanteGastoDao.obtenerParticipantes(gastoCompartidoId)
    }

    fun obtenerCantidadAPagar(gastoCompartidoId: Int, persona: String): LiveData<Double> {
        return participanteGastoDao.obtenerCantidadAPagar(gastoCompartidoId, persona)
    }

    suspend fun borrarGasto(gasto: GastoCompartido) {
        participanteGastoDao.borrarParticipantesPorGasto(gasto.id)
        gastoCompartidoDao.borrarGasto(gasto)
    }

    fun calcularDeudas(gastoId: Int): LiveData<List<Deuda>> {
        return gastoCompartidoDao.obtenerPorId(gastoId).switchMap { gasto ->
            val deudas = MutableLiveData<List<Deuda>>()

            val personas = gasto.participantes.split(",").map { it.trim() }

            val listaDeudas = mutableListOf<Deuda>()

            for (persona in personas) {
                if (persona != gasto.quienPago) {
                    val cantidadAPagar = gasto.cantidad / personas.size

                    val deuda = Deuda(
                        deudor = persona,
                        acreedor = gasto.quienPago,
                        cantidad = cantidadAPagar,
                        concepto = gasto.nombre,
                        fecha = gasto.fecha
                    )
                    listaDeudas.add(deuda)
                }
            }

            deudas.value = listaDeudas
            deudas
        }
    }
}