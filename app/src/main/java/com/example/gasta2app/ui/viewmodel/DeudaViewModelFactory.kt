package com.example.gasta2app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gasta2app.data.repository.DeudaRepository

class DeudaViewModelFactory(
    private val repository: DeudaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DeudaViewModel(repository) as T
    }
}