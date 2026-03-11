package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gasta2app.model.GastoGrupo

@Composable
fun PantallaDetalleGrupo() {

    var descripcion by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var pagadoPor by remember { mutableStateOf("") }

    val listaGastos = remember { mutableStateListOf<GastoGrupo>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Añadir gasto")

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = pagadoPor,
            onValueChange = { pagadoPor = it },
            label = { Text("Quién pagó") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val cantidadDouble = cantidad.toDoubleOrNull()

                if (cantidadDouble != null) {

                    listaGastos.add(
                        GastoGrupo(
                            descripcion,
                            pagadoPor,
                            cantidadDouble
                        )
                    )

                    descripcion = ""
                    cantidad = ""
                }

                descripcion = ""
                cantidad = ""
            }
        ) {
            Text("Añadir gasto")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(listaGastos) { gasto ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(gasto.descripcion)
                        Text("Pagado por: ${gasto.pagadoPor}")
                        Text("${gasto.cantidad} €")
                    }
                }
            }
        }
    }
}