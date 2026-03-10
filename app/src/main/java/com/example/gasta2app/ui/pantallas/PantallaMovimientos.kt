package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel

@Composable
fun PantallaMovimientos(
    navController: NavController,
    viewModel: MovimientoViewModel
) {

    val listaMovimientos by viewModel.listaMovimientos.observeAsState(emptyList())

    val balance = listaMovimientos.sumOf {
        if (it.tipo == "ingreso") it.cantidad else -it.cantidad
    }

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate("agregarMovimiento")
                }
            ) {
                Text("+")
            }

        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Balance total",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "$balance €",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {

                items(listaMovimientos) { movimiento ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {

                                Text(
                                    text = movimiento.descripcion,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(text = movimiento.tipo)
                            }

                            Column {

                                Text(text = "${movimiento.cantidad} €")

                                Button(
                                    onClick = {
                                        viewModel.eliminarMovimiento(movimiento)
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
    }
}