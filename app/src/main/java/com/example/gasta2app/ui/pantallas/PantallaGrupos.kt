package com.example.gasta2app.ui.pantallas

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
import androidx.navigation.NavController
import com.example.gasta2app.model.Grupo
import com.example.gasta2app.ui.theme.AzulClaroSuave

@Composable
fun PantallaGrupos(navController: NavController) {

    val listaGrupos = remember { mutableStateListOf<Grupo>() }
    var mostrarDialogoCrearGrupo by remember { mutableStateOf(false) }

    Scaffold(
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
                                if (grupo.participantes.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Participantes: ${grupo.participantes.joinToString(", ")}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                            FilledTonalButton(
                                onClick = {
                                    val participantesStr =
                                        grupo.participantes.joinToString("|")
                                    navController.navigate(
                                        "grupoDetalle/${grupo.nombre}/$participantesStr"
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
                listaGrupos.add(Grupo(nombre = nombre, participantes = participantes))
                mostrarDialogoCrearGrupo = false
            }
        )
    }
}