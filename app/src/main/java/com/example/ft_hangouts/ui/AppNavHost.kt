package com.example.ft_hangouts.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.ui.StartScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    contactDao: ContactDao)
{
    NavHost(
        navController = navController,
        startDestination = "start")
    {
        composable("start") {
            StartScreen(navController = navController)
        }
        composable("chat") {
            ChatScreen(navController = navController)
        }
        composable("contact") {
            ContactScreen(navController = navController)
        }
        composable("contactlist") {
            ContactListScreen(navController = navController)
        }
        composable("main") {
            MainScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
    }
}