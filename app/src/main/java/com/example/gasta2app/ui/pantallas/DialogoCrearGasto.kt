package com.example.gasta2app.ui.pantallas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DialogoCrearGrupo(
    onCerrar: () -> Unit,
    onCrear: (String, List<String>) -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var nombreParticipante by remember { mutableStateOf("") }
    val participantes = remember { mutableStateListOf<String>() }

    AlertDialog(
        onDismissRequest = { onCerrar() },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = { onCerrar() }) {
                    Text("Cancelar")
                }
                Button(
                    onClick = {
                        if (nombre.isNotBlank()) {
                            onCrear(nombre, participantes.toList())
                        }
                    }
                ) {
                    Text("Crear")
                }
            }
        },
        title = {
            Text("Crear grupo")
        },
        text = {
            Column {

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del grupo") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Participantes", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    OutlinedTextField(
                        value = nombreParticipante,
                        onValueChange = { nombreParticipante = it },
                        label = { Text("Nombre del participante") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (nombreParticipante.isNotBlank()) {
                                participantes.add(nombreParticipante)
                                nombreParticipante = ""
                            }
                        }
                    ) {
                        Text("Añadir")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                participantes.forEach { p ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(p, style = MaterialTheme.typography.bodyMedium)
                        IconButton(
                            onClick = { participantes.remove(p) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Eliminar participante",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    )
}