package com.example.gasta2app.ui.pantallas

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.BarChart

@Composable
fun BarraInferior(navController: NavController) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == "movimientos",
            onClick = { navController.navigate("movimientos") },
            icon = { Icon(Icons.Filled.List, contentDescription = null) },
            label = { Text("Movimientos") }
        )

        NavigationBarItem(
            selected = currentRoute == "estadisticas",
            onClick = { navController.navigate("estadisticas") },
            icon = { Icon(Icons.Filled.BarChart, contentDescription = null) },
            label = { Text("Estadísticas") }
        )

        NavigationBarItem(
            selected = currentRoute == "deudas",
            onClick = { navController.navigate("deudas") },
            icon = { Icon(Icons.Filled.AccountBalance, contentDescription = null) },
            label = { Text("Deudas") }
        )

        NavigationBarItem(
            selected = currentRoute == "grupos",
            onClick = { navController.navigate("grupos") },
            icon = { Icon(Icons.Filled.Group, contentDescription = null) },
            label = { Text("Grupos") }
        )

    }
}