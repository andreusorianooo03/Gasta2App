package com.example.gasta2app.data.repository

import com.example.gasta2app.data.dao.MovimientoDao
import com.example.gasta2app.model.Movimiento

class MovimientoRepository(private val dao: MovimientoDao) {

    val listaMovimientos = dao.obtenerMovimientos()

    suspend fun insertarMovimiento(movimiento: Movimiento) {
        dao.insertarMovimiento(movimiento)
    }

    suspend fun eliminarMovimiento(movimiento: Movimiento) {
        dao.eliminarMovimiento(movimiento)
    }
}