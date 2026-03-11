package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gasta2app.model.Categorias
import com.example.gasta2app.model.Movimiento
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel

@Composable
fun PantallaAgregarMovimiento(
    navController: NavController,
    viewModel: MovimientoViewModel
) {

    var descripcion by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("gasto") }
    var categoriaSeleccionada by remember { mutableStateOf("Otros") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Nuevo movimiento",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Tipo de movimiento")

        Row {

            Button(
                onClick = { tipo = "gasto" }
            ) {
                Text("Gasto")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { tipo = "ingreso" }
            ) {
                Text("Ingreso")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Categoría")

        LazyColumn(
            modifier = Modifier.height(200.dp)
        ) {

            items(Categorias.lista) { categoria ->

                Button(
                    onClick = {
                        categoriaSeleccionada = categoria.nombre
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {

                    Text("${categoria.icono} ${categoria.nombre}")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (descripcion.isNotEmpty() && cantidad.isNotEmpty()) {

                    val movimiento = Movimiento(

                        tipo = tipo,

                        cantidad = cantidad.toDouble(),

                        categoria = categoriaSeleccionada,

                        descripcion = descripcion
                    )

                    viewModel.insertarMovimiento(movimiento)

                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Guardar movimiento")
        }
    }
}