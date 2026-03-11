package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gasta2app.model.Grupo

@Composable
fun PantallaCrearGrupo(navController: NavController) {

    var nombreGrupo by remember { mutableStateOf("") }
    var participante by remember { mutableStateOf("") }

    val listaParticipantes = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Crear grupo", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nombreGrupo,
            onValueChange = { nombreGrupo = it },
            label = { Text("Nombre del grupo") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {

            OutlinedTextField(
                value = participante,
                onValueChange = { participante = it },
                label = { Text("Participante") }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {

                    listaParticipantes.add(participante)

                    participante = ""
                }
            ) {
                Text("Añadir")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Participantes:")

        listaParticipantes.forEach {

            Text(it)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val grupo = Grupo(
                    nombre = nombreGrupo,
                    participantes = listaParticipantes
                )

                navController.popBackStack()
            }
        ) {

            Text("Crear grupo")
        }
    }
}