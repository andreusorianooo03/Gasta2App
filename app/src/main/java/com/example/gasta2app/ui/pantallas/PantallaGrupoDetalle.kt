package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gasta2app.ui.theme.AzulClaroSuave

data class GastoGrupo(
    val descripcion: String,
    val cantidad: Double,
    val pagador: String
)

@Composable
fun PantallaGrupoDetalle(
    nombreGrupo: String,
    participantesIniciales: List<String>
) {

    val participantes = remember {
        mutableStateListOf<String>().apply { addAll(participantesIniciales) }
    }
    val gastos = remember { mutableStateListOf<GastoGrupo>() }

    var descripcion by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var pagador by remember { mutableStateOf("") }

    var deudasEntreParticipantes by remember { mutableStateOf<List<String>>(emptyList()) }
    var mostrarDialogoResumen by remember { mutableStateOf(false) }

    var indiceEditando by remember { mutableStateOf<Int?>(null) }
    var descripcionEdit by remember { mutableStateOf("") }
    var cantidadEdit by remember { mutableStateOf("") }
    var pagadorEdit by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulClaroSuave)
            .padding(16.dp)
    ) {

        Text(
            text = nombreGrupo,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = "Participantes",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                participantes.forEach { participante ->
                    Text(
                        text = participante,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = "Gastos",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                SeccionAnadirGasto(
                    descripcion = descripcion,
                    onDescripcionChange = { descripcion = it },
                    cantidad = cantidad,
                    onCantidadChange = { cantidad = it },
                    participantes = participantes,
                    onPagadorSeleccionado = { pagador = it },
                    onAgregarGasto = {
                        if (descripcion.isNotBlank() && cantidad.isNotBlank() && pagador.isNotBlank()) {
                            val cantidadNormalizada = cantidad.replace(",", ".")
                            val cantidadDouble = cantidadNormalizada.toDoubleOrNull()

                            if (cantidadDouble != null && cantidadDouble > 0.0) {
                                gastos.add(
                                    GastoGrupo(
                                        descripcion = descripcion,
                                        cantidad = cantidadDouble,
                                        pagador = pagador
                                    )
                                )
                                descripcion = ""
                                cantidad = ""
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ListaGastos(
                    gastos = gastos,
                    onEditar = { index, gasto ->
                        indiceEditando = index
                        descripcionEdit = gasto.descripcion
                        cantidadEdit = gasto.cantidad.toString()
                        pagadorEdit = gasto.pagador
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                deudasEntreParticipantes = calcularDeudasEntreParticipantes(
                    participantes = participantes,
                    gastos = gastos
                )
                if (deudasEntreParticipantes.isNotEmpty()) {
                    mostrarDialogoResumen = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }
    }

    if (mostrarDialogoResumen) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoResumen = false },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarDialogoResumen = false
                    }
                ) {
                    Text("Cerrar")
                }
            },
            title = {
                Text("Resumen de deudas")
            },
            text = {
                Column {
                    deudasEntreParticipantes.forEach { linea ->
                        Text(linea)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        )
    }

    if (indiceEditando != null) {
        AlertDialog(
            onDismissRequest = { indiceEditando = null },
            confirmButton = {
                Button(
                    onClick = {
                        val index = indiceEditando
                        if (index != null &&
                            descripcionEdit.isNotBlank() &&
                            cantidadEdit.isNotBlank() &&
                            pagadorEdit.isNotBlank()
                        ) {
                            val cantidadNormalizada = cantidadEdit.replace(",", ".")
                            val cantidadDouble = cantidadNormalizada.toDoubleOrNull()
                            if (cantidadDouble != null && cantidadDouble > 0.0) {
                                gastos[index] = GastoGrupo(
                                    descripcion = descripcionEdit,
                                    cantidad = cantidadDouble,
                                    pagador = pagadorEdit
                                )
                                indiceEditando = null
                            }
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { indiceEditando = null }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Editar gasto") },
            text = {
                Column {
                    OutlinedTextField(
                        value = descripcionEdit,
                        onValueChange = { descripcionEdit = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = cantidadEdit,
                        onValueChange = { cantidadEdit = it },
                        label = { Text("Cantidad") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (participantes.isNotEmpty()) {
                        MenuDesplegable(
                            opciones = participantes,
                            onSeleccionar = { pagadorEdit = it }
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun SeccionAnadirGasto(
    descripcion: String,
    onDescripcionChange: (String) -> Unit,
    cantidad: String,
    onCantidadChange: (String) -> Unit,
    participantes: List<String>,
    onPagadorSeleccionado: (String) -> Unit,
    onAgregarGasto: () -> Unit
) {
    Column {

        OutlinedTextField(
            value = descripcion,
            onValueChange = onDescripcionChange,
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = onCantidadChange,
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (participantes.isNotEmpty()) {
            MenuDesplegable(opciones = participantes, onSeleccionar = onPagadorSeleccionado)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onAgregarGasto) {
            Text("Añadir gasto")
        }
    }
}

@Composable
private fun ListaGastos(
    gastos: List<GastoGrupo>,
    onEditar: (Int, GastoGrupo) -> Unit
) {
    LazyColumn {
        itemsIndexed(gastos) { index, gasto ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${gasto.descripcion} - ${gasto.cantidad}€ (${gasto.pagador})",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = { onEditar(index, gasto) }) {
                    Text("Editar")
                }
            }
        }
    }
}

private fun calcularDeudasEntreParticipantes(
    participantes: List<String>,
    gastos: List<GastoGrupo>
): List<String> {
    if (participantes.isEmpty() || gastos.isEmpty()) return emptyList()

    val totalGastado = gastos.sumOf { it.cantidad }
    val aportePorPersona = totalGastado / participantes.size

    val pagadoPorPersona = mutableMapOf<String, Double>().apply {
        participantes.forEach { this[it] = 0.0 }
    }
    gastos.forEach { gasto ->
        pagadoPorPersona[gasto.pagador] =
            (pagadoPorPersona[gasto.pagador] ?: 0.0) + gasto.cantidad
    }

    val resultados = mutableListOf<String>()

    participantes.forEach { nombre ->
        val pagado = pagadoPorPersona[nombre] ?: 0.0
        val saldo = pagado - aportePorPersona

        when {
            saldo < -0.005 -> resultados.add(
                "$nombre debe ${"%.2f".format(-saldo)} €"
            )
            saldo > 0.005 -> resultados.add(
                "A $nombre le deben ${"%.2f".format(saldo)} €"
            )
            else -> resultados.add(
                "$nombre está equilibrado, no debe nada."
            )
        }
    }

    if (resultados.isEmpty()) {
        resultados.add("Todos han aportado lo mismo, no hay deudas pendientes.")
    }

    return resultados
}