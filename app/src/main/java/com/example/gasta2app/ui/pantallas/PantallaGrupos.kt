package com.example.gasta2app.ui.pantallas

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.gasta2app.ui.theme.AzulClaroSuave
import com.example.gasta2app.ui.viewmodel.GastoConjuntoViewModel

@Composable
fun PantallaGrupos(
    navController: NavController,
    viewModel: GastoConjuntoViewModel
) {
    val listaGrupos by viewModel.listaGrupos.observeAsState(emptyList())
    val mensajeUi by viewModel.mensajeUi.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var mostrarDialogoCrearGrupo by remember { mutableStateOf(false) }

    LaunchedEffect(mensajeUi) {
        val mensaje = mensajeUi ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(mensaje)
        viewModel.limpiarMensajeUi()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    mostrarDialogoCrearGrupo = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Crear gasto conjunto"
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AzulClaroSuave)
                .padding(padding)
        ) {

            Text(
                text = "Cuentas conjuntas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {

                items(listaGrupos) { grupo ->
                    val participantes by viewModel
                        .obtenerParticipantes(grupo.id)
                        .observeAsState(emptyList())

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {
                                Text(
                                    text = grupo.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                if (participantes.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Participantes: ${
                                            participantes.joinToString(", ") { it.nombre }
                                        }",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                            FilledTonalButton(
                                onClick = {
                                    navController.navigate(
                                        "grupoDetalle/${grupo.id}/${Uri.encode(grupo.nombre)}"
                                    )
                                }
                            ) {
                                Text("Entrar")
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = "Entrar"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialogoCrearGrupo) {
        DialogoCrearGrupo(
            onCerrar = { mostrarDialogoCrearGrupo = false },
            onCrear = { nombre, participantes ->
                viewModel.crearGrupo(nombre = nombre, participantes = participantes)
                if (nombre.isNotBlank() && participantes.any { it.isNotBlank() }) {
                    mostrarDialogoCrearGrupo = false
                }
            }
        )
    }
}