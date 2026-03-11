package com.example.gasta2app.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gasta2app.model.Grupo

@Composable
fun PantallaGrupos(navController: NavController) {

    val listaGrupos = remember { mutableStateListOf<Grupo>() }

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate("crearGrupo")
                }
            ) {
                Text("+")
            }

        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Cuentas conjuntas",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {

                items(listaGrupos) { grupo ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(grupo.nombre)

                            Button(
                                onClick = {
                                    navController.navigate("grupoDetalle/${grupo.nombre}")
                                }
                            ) {
                                Text("Entrar")
                            }
                        }
                    }
                }
            }
        }
    }
}