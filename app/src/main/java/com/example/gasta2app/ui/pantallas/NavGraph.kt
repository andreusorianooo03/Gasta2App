package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gasta2app.R
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel
import com.example.gasta2app.ui.viewmodel.DeudaViewModel
import com.example.gasta2app.ui.viewmodel.GastoConjuntoViewModel
import kotlinx.coroutines.delay

@Composable
fun NavGraph(
    viewModel: MovimientoViewModel,
    deudaViewModel: DeudaViewModel,
    gastoConjuntoViewModel: GastoConjuntoViewModel
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "splash") {
                BarraInferior(navController)
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("splash") {
                SplashScreen {
                    navController.navigate("movimientos") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }

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

@Composable
private fun SplashScreen(onFinish: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500)
        onFinish()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de la app",
            modifier = Modifier.size(120.dp)
        )
    }
}