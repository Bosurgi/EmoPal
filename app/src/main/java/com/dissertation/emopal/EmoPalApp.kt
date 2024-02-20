package com.dissertation.emopal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dissertation.emopal.routes.Routes
import com.dissertation.emopal.ui.screens.diary.DiaryScreen
import com.dissertation.emopal.ui.screens.home.HomeScreen

@Composable
fun EmoPalApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold() { innerPadding ->

        // Set the NavHost to navigate between screens
        NavHost(
            navController = navController,
            // Start Destination - Home Screen
            startDestination = Routes.HOME.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Composing the Home Screen
            composable(route = Routes.HOME.name) {
                HomeScreen(
                    onDiaryButtonClicked = {
                        navController.navigate(Routes.DIARY.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        // TODO: Change padding to Resource Value Dimensions Example -> (R.dimen.padding)
                        .padding(16.dp)
                )
            }
            // TODO: Add the other screens below
            composable(route = Routes.DIARY.name) {
                DiaryScreen(onBackButtonClicked = { navController.navigate(Routes.HOME.name) })
            }
        }
    } // End of Scaffold lambda
} // End of EmoPalApp Composable
