package com.example.gasta2app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.gasta2app.data.local.BaseDeDatos
import com.example.gasta2app.model.GastoConjunto
import com.example.gasta2app.model.Participante
import kotlinx.coroutines.launch

class GastoConjuntoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = BaseDeDatos.obtenerBaseDeDatos(application)

    val listaGastos = db.gastoConjuntoDao().obtenerTodos()

    fun crearGastoConjunto(nombre: String, participantes: List<String>) {

        viewModelScope.launch {

            val gasto = GastoConjunto(nombre = nombre)

            db.gastoConjuntoDao().insertar(gasto)

            val id = db.gastoConjuntoDao().obtenerUltimoId()

            participantes.forEach {

                db.participanteDao().insertar(
                    Participante(
                        nombre = it,
                        gastoConjuntoId = id
                    )
                )

            }

        }

    }

}