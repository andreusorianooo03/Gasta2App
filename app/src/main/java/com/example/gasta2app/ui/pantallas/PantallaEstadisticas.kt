package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel

@Composable
fun PantallaEstadisticas(viewModel: MovimientoViewModel) {

    val movimientos by viewModel.listaMovimientos.observeAsState(emptyList())

    val totalGastos = movimientos
        .filter { it.tipo == "gasto" }
        .sumOf { it.cantidad }

    val totalIngresos = movimientos
        .filter { it.tipo == "ingreso" }
        .sumOf { it.cantidad }

    val balance = totalIngresos - totalGastos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Estadísticas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text("Total ingresos")
                Text("$totalIngresos €")

            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text("Total gastos")
                Text("$totalGastos €")

            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text("Balance")
                Text("$balance €")

            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Resumen",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Ingresos: $totalIngresos €")
        Text("Gastos: $totalGastos €")
        Text("Balance: $balance €")

    }
}