package com.example.gasta2app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gasta2app.data.repository.MovimientoRepository
import com.example.gasta2app.model.Movimiento
import kotlinx.coroutines.launch

class MovimientoViewModel(private val repository: MovimientoRepository) : ViewModel() {

    val listaMovimientos = repository.listaMovimientos

    fun insertarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.insertarMovimiento(movimiento)
        }
    }

    fun eliminarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.eliminarMovimiento(movimiento)
        }
    }
}