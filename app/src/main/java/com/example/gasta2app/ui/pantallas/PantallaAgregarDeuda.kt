package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gasta2app.model.Deuda
import com.example.gasta2app.ui.theme.AzulClaroSuave
import com.example.gasta2app.ui.viewmodel.DeudaViewModel

@Composable
fun PantallaAgregarDeuda(
    navController: NavController,
    viewModel: DeudaViewModel
) {

    var persona by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("me deben") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulClaroSuave)
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Añadir deuda",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                OutlinedTextField(
                    value = persona,
                    onValueChange = { persona = it },
                    label = { Text("Persona") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tipo de deuda",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilledTonalButton(
                        onClick = { tipo = "me deben" },
                        modifier = Modifier.weight(1f),
                        colors = if (tipo == "me deben") {
                            ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            ButtonDefaults.filledTonalButtonColors()
                        }
                    ) {
                        Text("Me deben")
                    }

                    FilledTonalButton(
                        onClick = { tipo = "debo" },
                        modifier = Modifier.weight(1f),
                        colors = if (tipo == "debo") {
                            ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            ButtonDefaults.filledTonalButtonColors()
                        }
                    ) {
                        Text("Debo")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (persona.isNotBlank() && cantidad.isNotBlank()) {

                    val cantidadNormalizada = cantidad.replace(",", ".")
                    val cantidadDouble = cantidadNormalizada.toDoubleOrNull()

                    if (cantidadDouble != null && cantidadDouble > 0.0) {
                        val deuda = Deuda(
                            persona = persona,
                            cantidad = cantidadDouble,
                            tipo = tipo
                        )

                        viewModel.insertar(deuda)
                        navController.popBackStack()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar deuda")
        }
    }
}