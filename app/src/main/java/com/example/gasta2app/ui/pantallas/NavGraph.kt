package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel
import com.example.gasta2app.ui.viewmodel.DeudaViewModel
import com.example.gasta2app.ui.viewmodel.GastoConjuntoViewModel

@Composable
fun NavGraph(
    viewModel: MovimientoViewModel,
    deudaViewModel: DeudaViewModel,
    gastoConjuntoViewModel: GastoConjuntoViewModel
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
                PantallaGrupos(
                    navController = navController,
                    viewModel = gastoConjuntoViewModel
                )
            }

            composable(
                route = "grupoDetalle/{grupoId}/{nombre}",
                arguments = listOf(
                    navArgument("grupoId") { type = NavType.IntType },
                    navArgument("nombre") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val grupoId = backStackEntry.arguments?.getInt("grupoId") ?: 0
                val nombre = backStackEntry.arguments?.getString("nombre").orEmpty()

                PantallaGrupoDetalle(
                    grupoId = grupoId,
                    nombreGrupo = nombre,
                    viewModel = gastoConjuntoViewModel
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