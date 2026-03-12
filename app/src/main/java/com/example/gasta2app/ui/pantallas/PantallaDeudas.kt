package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gasta2app.model.Deuda
import com.example.gasta2app.ui.viewmodel.DeudaViewModel

@Composable
fun PantallaDeudas(viewModel: DeudaViewModel = viewModel()) {

    val deudas by viewModel.listaDeudas.observeAsState(emptyList())

    var persona by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("me deben") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Añadir deuda")

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = persona,
            onValueChange = { persona = it },
            label = { Text("Persona") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Button(onClick = { tipo = "me deben" }) {
                Text("Me deben")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = { tipo = "debo" }) {
                Text("Debo")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (persona.isNotBlank() && cantidad.isNotBlank()) {

                    val deuda = Deuda(
                        persona = persona,
                        cantidad = cantidad.toDouble(),
                        tipo = tipo
                    )

                    viewModel.insertar(deuda)

                    persona = ""
                    cantidad = ""
                }
            }
        ) {
            Text("Añadir deuda")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Lista de deudas")

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(deudas) { deuda ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {
                            Text(deuda.persona)
                            Text("${deuda.cantidad} €")
                            Text(deuda.tipo)
                        }

                        Button(
                            onClick = {
                                viewModel.eliminar(deuda)
                            }
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}