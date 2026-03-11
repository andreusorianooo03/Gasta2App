package com.example.gasta2app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Gasto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cantidad: Double,
    val categoria: String,
    val descripcion: String,
    val fecha: Long,
    val esIngreso: Boolean = false
)