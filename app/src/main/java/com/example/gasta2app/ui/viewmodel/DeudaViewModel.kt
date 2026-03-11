package com.example.gasta2app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gasta2app.data.repository.DeudaRepository
import com.example.gasta2app.model.Deuda
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf

class DeudaViewModel(private val repository: DeudaRepository) : ViewModel() {

    var listaDeudas = mutableStateListOf<Deuda>()

    fun cargarDeudas() {

        viewModelScope.launch {
            listaDeudas.clear()
            listaDeudas.addAll(repository.obtenerTodas())
        }
    }

    fun agregarDeuda(deuda: Deuda) {

        viewModelScope.launch {
            repository.insertar(deuda)
            cargarDeudas()
        }
    }

    fun eliminarDeuda(deuda: Deuda) {

        viewModelScope.launch {
            repository.eliminar(deuda)
            cargarDeudas()
        }
    }
}