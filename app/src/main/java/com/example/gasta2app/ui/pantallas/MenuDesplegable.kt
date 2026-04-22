package com.example.gasta2app.ui.pantallas

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun MenuDesplegable(opciones: List<String>, onSeleccionar: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    var seleccionado by remember { mutableStateOf("") }

    OutlinedButton(
        onClick = { expanded = true },
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(if (seleccionado == "") "Quién pagó" else seleccionado)

    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {

        opciones.forEach {

            DropdownMenuItem(
                text = { Text(it) },
                onClick = {

                    seleccionado = it

                    expanded = false

                    onSeleccionar(it)

                }
            )

        }

    }

}