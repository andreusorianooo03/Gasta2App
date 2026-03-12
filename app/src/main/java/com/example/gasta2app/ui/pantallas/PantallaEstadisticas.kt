package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gasta2app.ui.theme.AzulClaroPrimario
import com.example.gasta2app.ui.theme.AzulClaroSuave
import com.example.gasta2app.ui.theme.VerdeSuave
import com.example.gasta2app.ui.theme.RojoSuave
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
            .background(AzulClaroSuave)
            .padding(16.dp)
    ) {

        Text(
            text = "Estadísticas",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = AzulClaroPrimario
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = String.format("%.2f €", balance),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = VerdeSuave
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text("Total ingresos", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = String.format("%.2f €", totalIngresos),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = RojoSuave
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text("Total gastos", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = String.format("%.2f €", totalGastos),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}