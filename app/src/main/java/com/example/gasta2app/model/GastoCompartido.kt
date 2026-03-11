package com.example.gasta2app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos_compartidos")
data class GastoCompartido(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val quienPago: String,
    val cantidad: Double,
    val descripcion: String,
    val fecha: Long,
    val participantes: String = ""
)