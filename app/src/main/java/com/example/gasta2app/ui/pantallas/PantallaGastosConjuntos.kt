package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaGastosConjuntos() {

    var mostrarDialogo by remember { mutableStateOf(true) }

    val grupos = remember { mutableStateListOf<String>() }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "Gastos Conjuntos",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {

                items(grupos) { grupo ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                        ,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {

                        Text(
                            text = grupo,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )

                    }

                }

            }

        }

    }

    if (mostrarDialogo) {

        DialogoCrearGrupo(
            onCerrar = { mostrarDialogo = false },
            onCrear = { nombre, _ ->

                grupos.add(nombre)

                mostrarDialogo = false
            }
        )
    }

}