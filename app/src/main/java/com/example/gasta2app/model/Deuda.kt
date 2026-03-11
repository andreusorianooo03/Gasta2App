package com.example.gasta2app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deudas")
data class Deuda(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val persona: String,

    val cantidad: Double,

    val tipo: String,

    val pagada: Boolean = false
)