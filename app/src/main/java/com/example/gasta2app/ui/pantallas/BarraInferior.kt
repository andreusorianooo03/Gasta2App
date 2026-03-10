package com.example.gasta2app.ui.pantallas

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Group

@Composable
fun BarraInferior(navController: NavController) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == "movimientos",
            onClick = { navController.navigate("movimientos") },
            label = { Text("Movimientos") },
            icon = { Icon(Icons.Filled.List, contentDescription = "Movimientos") }
        )

        NavigationBarItem(
            selected = currentRoute == "deudas",
            onClick = { navController.navigate("deudas") },
            label = { Text("Deudas") },
            icon = { Icon(Icons.Filled.AccountBalance, contentDescription = "Deudas") }
        )

        NavigationBarItem(
            selected = currentRoute == "grupos",
            onClick = { navController.navigate("grupos") },
            label = { Text("Grupos") },
            icon = { Icon(Icons.Filled.Group, contentDescription = "Grupos") }
        )
    }
}