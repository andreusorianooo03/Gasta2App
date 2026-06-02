package com.example.gasta2app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "participantes")
data class Participante(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val gastoConjuntoId: Int
)