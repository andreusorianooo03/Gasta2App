package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                PantallaDeudas(
                    viewModel = deudaViewModel,
                    navController = navController
                )
            }

            composable("grupos") {
                PantallaGrupos(navController)
            }

            composable("grupoDetalle/{nombre}/{participantes}") { backStackEntry ->

                val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                val participantesStr =
                    backStackEntry.arguments?.getString("participantes") ?: ""
                val participantes = if (participantesStr.isBlank()) {
                    emptyList()
                } else {
                    participantesStr.split("|")
                }

                PantallaGrupoDetalle(
                    nombreGrupo = nombre,
                    participantesIniciales = participantes
                )
            }

            composable("agregarMovimiento") {
                PantallaAgregarMovimiento(navController, viewModel)
            }

            composable("agregarDeuda") {
                PantallaAgregarDeuda(
                    navController = navController,
                    viewModel = deudaViewModel
                )
            }
        }
    }
}