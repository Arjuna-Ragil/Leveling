package com.example.leveling.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leveling.login.Welcome
import com.example.leveling.login.Login
import com.example.leveling.login.LoginViewModel
import com.example.leveling.ui.theme.LevelingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LevelingTheme() {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navControllerSecondary = rememberNavController()
    NavHost(navController = navControllerSecondary, startDestination = "welcome") {
        composable("welcome") { Welcome(navControllerSecondary, LoginViewModel()) }
        composable("login") { Login(navControllerSecondary) }
        composable("main") { NavBar(navControllerSecondary) }
    }
}