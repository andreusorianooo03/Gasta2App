package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gasta2app.model.Movimiento
import com.example.gasta2app.ui.theme.AzulClaroSuave
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel

@Composable
fun PantallaAgregarMovimiento(
    navController: NavController,
    viewModel: MovimientoViewModel
) {

    var descripcion by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("gasto") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulClaroSuave)
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Nuevo movimiento",
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
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
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
                    text = "Tipo de movimiento",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilledTonalButton(
                        onClick = { tipo = "gasto" },
                        modifier = Modifier.weight(1f),
                        colors = if (tipo == "gasto") {
                            ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            ButtonDefaults.filledTonalButtonColors()
                        }
                    ) {
                        Text("Gasto")
                    }

                    FilledTonalButton(
                        onClick = { tipo = "ingreso" },
                        modifier = Modifier.weight(1f),
                        colors = if (tipo == "ingreso") {
                            ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            ButtonDefaults.filledTonalButtonColors()
                        }
                    ) {
                        Text("Ingreso")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                if (descripcion.isNotEmpty() && cantidad.isNotEmpty()) {

                    val cantidadNormalizada = cantidad.replace(",", ".")
                    val cantidadDouble = cantidadNormalizada.toDoubleOrNull()

                    if (cantidadDouble != null && cantidadDouble > 0.0) {
                        val movimiento = Movimiento(
                            tipo = tipo,
                            cantidad = cantidadDouble,
                            categoria = "",
                            descripcion = descripcion
                        )

                        viewModel.insertarMovimiento(movimiento)
                        navController.popBackStack()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Guardar movimiento")
        }
    }
}