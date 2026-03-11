package com.example.gasta2app.data.repository

import androidx.lifecycle.LiveData
import com.example.gasta2app.data.dao.DeudaDao
import com.example.gasta2app.model.Deuda

class DeudaRepository(private val deudaDao: DeudaDao) {

    val listaDeudas: LiveData<List<Deuda>> = deudaDao.obtenerTodas()

    suspend fun insertar(deuda: Deuda) {
        deudaDao.insertar(deuda)
    }

    suspend fun eliminar(deuda: Deuda) {
        deudaDao.eliminar(deuda)
    }
}