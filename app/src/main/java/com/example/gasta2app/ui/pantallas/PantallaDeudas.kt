package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gasta2app.model.Deuda
import com.example.gasta2app.ui.viewmodel.DeudaViewModel

@Composable
fun PantallaDeudas(viewModel: DeudaViewModel) {

    var persona by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("meDebe") }

    LaunchedEffect(Unit) {
        viewModel.cargarDeudas()
    }

    val meDeben = viewModel.listaDeudas.filter { it.tipo == "meDebe" }
    val debo = viewModel.listaDeudas.filter { it.tipo == "debo" }

    val totalMeDeben = meDeben.sumOf { it.cantidad }
    val totalDebo = debo.sumOf { it.cantidad }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Deudas", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = persona,
            onValueChange = { persona = it },
            label = { Text("Persona") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row {

            Button(onClick = { tipo = "meDebe" }) {
                Text("Me debe")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = { tipo = "debo" }) {
                Text("Debo")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {

                if (persona.isNotEmpty() && cantidad.isNotEmpty()) {

                    viewModel.agregarDeuda(
                        Deuda(
                            persona = persona,
                            cantidad = cantidad.toDouble(),
                            tipo = tipo
                        )
                    )

                    persona = ""
                    cantidad = ""
                }
            }
        ) {

            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(5.dp))
            Text("Añadir deuda")
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text("💰 Me deben: ${totalMeDeben}€", fontSize = 18.sp)

        LazyColumn {

            items(meDeben) { deuda ->
                ItemDeuda(deuda, viewModel)
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("💸 Debo: ${totalDebo}€", fontSize = 18.sp)

        LazyColumn {

            items(debo) { deuda ->
                ItemDeuda(deuda, viewModel)
            }

        }
    }
}

@Composable
fun ItemDeuda(
    deuda: Deuda,
    viewModel: DeudaViewModel
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(deuda.persona)

                Text("${deuda.cantidad}€")

                Text(deuda.tipo)
            }

            IconButton(
                onClick = {
                    viewModel.eliminarDeuda(deuda)
                }
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}