package com.example.gasta2app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "participantes_gasto")
data class ParticipanteGasto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val gastoCompartidoId: Int,
    val nombrePersona: String,
    val cantidadAPagar: Double
)