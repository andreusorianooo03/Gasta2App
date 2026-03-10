package com.example.gasta2app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gasta2app.data.local.BaseDeDatos
import com.example.gasta2app.data.repository.MovimientoRepository
import com.example.gasta2app.ui.pantallas.BarraInferior
import com.example.gasta2app.ui.pantallas.NavGraph
import com.example.gasta2app.ui.pantallas.PantallaDeudas
import com.example.gasta2app.ui.pantallas.PantallaGrupos
import com.example.gasta2app.ui.theme.Gasta2AppTheme
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel
import com.example.gasta2app.ui.viewmodel.MovimientoViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        val repository = MovimientoRepository(baseDeDatos.movimientoDao())
        val factory = MovimientoViewModelFactory(repository)

        val viewModel = ViewModelProvider(this, factory)
            .get(MovimientoViewModel::class.java)

        setContent {

            val navController = rememberNavController()

            Gasta2AppTheme {

                Scaffold(
                    bottomBar = {
                        BarraInferior(navController)
                    }
                ) { paddingValues ->

                    NavHost(
                        navController = navController,
                        startDestination = "movimientos",
                        modifier = androidx.compose.ui.Modifier.padding(paddingValues)
                    ) {

                        composable("movimientos") {
                            NavGraph(viewModel)
                        }

                        composable("deudas") {
                            PantallaDeudas()
                        }

                        composable("grupos") {
                            PantallaGrupos()
                        }
                    }
                }
            }
        }
    }
}