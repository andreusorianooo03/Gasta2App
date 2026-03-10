package com.example.gasta2app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gasta2app.data.repository.MovimientoRepository

class MovimientoViewModelFactory(
    private val repository: MovimientoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovimientoViewModel(repository) as T
    }
}