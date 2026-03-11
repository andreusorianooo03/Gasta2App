package com.example.gasta2app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gasta2app.data.repository.DeudaRepository
import com.example.gasta2app.model.Deuda
import kotlinx.coroutines.launch

class DeudaViewModel(private val repository: DeudaRepository) : ViewModel() {

    val listaDeudas: LiveData<List<Deuda>> = repository.listaDeudas

    fun insertar(deuda: Deuda) {
        viewModelScope.launch {
            repository.insertar(deuda)
        }
    }

    fun eliminar(deuda: Deuda) {
        viewModelScope.launch {
            repository.eliminar(deuda)
        }
    }
}