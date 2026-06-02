package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gasta2app.model.Gasto
import com.example.gasta2app.ui.theme.AzulClaroSuave
import com.example.gasta2app.ui.viewmodel.GastoConjuntoViewModel

@Composable
fun PantallaGrupoDetalle(
    grupoId: Int,
    nombreGrupo: String,
    viewModel: GastoConjuntoViewModel
) {
    val participantes by viewModel.obtenerParticipantes(grupoId).observeAsState(emptyList())
    val gastos by viewModel.obtenerGastos(grupoId).observeAsState(emptyList())
    val mensajeUi by viewModel.mensajeUi.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var descripcion by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var pagador by remember { mutableStateOf("") }
    var deudasEntreParticipantes by remember { mutableStateOf<List<String>>(emptyList()) }
    var mostrarDialogoResumen by remember { mutableStateOf(false) }

    LaunchedEffect(mensajeUi) {
        val mensaje = mensajeUi ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(mensaje)
        viewModel.limpiarMensajeUi()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AzulClaroSuave)
                .padding(padding)
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
                            text = participante.nombre,
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
                        participantes = participantes.map { it.nombre },
                        onPagadorSeleccionado = { pagador = it },
                        onAgregarGasto = {
                            viewModel.agregarGasto(
                                grupoId = grupoId,
                                descripcion = descripcion,
                                cantidadTexto = cantidad,
                                pagador = pagador
                            )
                            if (descripcion.isNotBlank() && cantidad.isNotBlank() && pagador.isNotBlank()) {
                                descripcion = ""
                                cantidad = ""
                                pagador = ""
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ListaGastos(gastos = gastos)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    deudasEntreParticipantes = calcularDeudasEntreParticipantes(
                        participantes = participantes.map { it.nombre },
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
    }

    if (mostrarDialogoResumen) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoResumen = false },
            confirmButton = {
                Button(onClick = { mostrarDialogoResumen = false }) {
                    Text("Cerrar")
                }
            },
            title = { Text("Resumen de deudas") },
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
    gastos: List<Gasto>
) {
    LazyColumn {
        items(gastos) { gasto ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${gasto.descripcion} - ${gasto.cantidad}€ (${gasto.pagadoPor})",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

private fun calcularDeudasEntreParticipantes(
    participantes: List<String>,
    gastos: List<Gasto>
): List<String> {
    if (participantes.isEmpty() || gastos.isEmpty()) return emptyList()

    val totalGastado = gastos.sumOf { it.cantidad }
    val aportePorPersona = totalGastado / participantes.size

    val pagadoPorPersona = mutableMapOf<String, Double>().apply {
        participantes.forEach { this[it] = 0.0 }
    }
    gastos.forEach { gasto ->
        pagadoPorPersona[gasto.pagadoPor] =
            (pagadoPorPersona[gasto.pagadoPor] ?: 0.0) + gasto.cantidad
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