package com.example.gasta2app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos_conjuntos")
data class GastoConjunto(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String
)