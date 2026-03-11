package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel
import com.example.gasta2app.ui.viewmodel.DeudaViewModel

@Composable
fun NavGraph(
    viewModel: MovimientoViewModel,
    deudaViewModel: DeudaViewModel
) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BarraInferior(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("agregarMovimiento")
                }
            ) {
                Text("+")
            }
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

            composable("estadisticas") {
                PantallaEstadisticas(viewModel)
            }

            composable("deudas") {
                PantallaDeudas(deudaViewModel)
            }

            composable("grupos") {
                PantallaGrupos(navController)
            }

            composable("agregarMovimiento") {
                PantallaAgregarMovimiento(navController, viewModel)
            }
        }
    }
}