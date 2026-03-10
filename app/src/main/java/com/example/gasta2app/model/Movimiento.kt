package com.example.gasta2app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimientos")
data class Movimiento(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val tipo: String,

    val cantidad: Double,

    val categoria: String,

    val descripcion: String
)