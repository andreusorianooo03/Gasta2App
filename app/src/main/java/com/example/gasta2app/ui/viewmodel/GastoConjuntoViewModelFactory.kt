package com.example.gasta2app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gasta2app.data.repository.GastoConjuntoRepository

class GastoConjuntoViewModelFactory(
    private val repository: GastoConjuntoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GastoConjuntoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GastoConjuntoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
