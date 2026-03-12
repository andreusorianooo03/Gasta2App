package com.example.gasta2app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Gasto(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val descripcion: String,

    val cantidad: Double,

    val pagadoPor: String,

    val gastoConjuntoId: Int
)