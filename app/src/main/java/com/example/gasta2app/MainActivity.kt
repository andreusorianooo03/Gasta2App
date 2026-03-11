package com.example.gasta2app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.gasta2app.data.local.BaseDeDatos
import com.example.gasta2app.data.repository.DeudaRepository
import com.example.gasta2app.data.repository.MovimientoRepository
import com.example.gasta2app.ui.pantallas.NavGraph
import com.example.gasta2app.ui.theme.Gasta2AppTheme
import com.example.gasta2app.ui.viewmodel.DeudaViewModel
import com.example.gasta2app.ui.viewmodel.DeudaViewModelFactory
import com.example.gasta2app.ui.viewmodel.MovimientoViewModel
import com.example.gasta2app.ui.viewmodel.MovimientoViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val baseDeDatos = BaseDeDatos.obtenerBaseDeDatos(this)
        val repository = MovimientoRepository(baseDeDatos.movimientoDao())
        val factory = MovimientoViewModelFactory(repository)
        val deudaRepository = DeudaRepository(baseDeDatos.deudaDao())
        val deudaFactory = DeudaViewModelFactory(deudaRepository)

        val viewModel = ViewModelProvider(this, factory)
            .get(MovimientoViewModel::class.java)

        val deudaViewModel = ViewModelProvider(this, deudaFactory)
            .get(DeudaViewModel::class.java)

        setContent {
            Gasta2AppTheme {
                NavGraph(viewModel = viewModel, deudaViewModel = deudaViewModel)
            }
        }
    }
}