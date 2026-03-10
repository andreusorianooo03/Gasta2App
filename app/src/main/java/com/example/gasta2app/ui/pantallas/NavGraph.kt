package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel

@Composable
fun NavGraph(viewModel: MovimientoViewModel) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BarraInferior(navController)
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "movimientos",
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("movimientos") {
                PantallaMovimientos(navController, viewModel)
            }

            composable("deudas") {
                PantallaDeudas()
            }

            composable("grupos") {
                PantallaGrupos()
            }

            composable("agregarMovimiento") {
                PantallaAgregarMovimiento(navController, viewModel)
            }

        }
    }
}