package com.example.gasta2app.data.repository

import com.example.gasta2app.data.local.DeudaDao
import com.example.gasta2app.model.Deuda

class DeudaRepository(private val deudaDao: DeudaDao) {

    suspend fun insertar(deuda: Deuda) {
        deudaDao.insertar(deuda)
    }

    suspend fun obtenerTodas(): List<Deuda> {
        return deudaDao.obtenerTodas()
    }

    suspend fun eliminar(deuda: Deuda) {
        deudaDao.eliminar(deuda)
    }
}