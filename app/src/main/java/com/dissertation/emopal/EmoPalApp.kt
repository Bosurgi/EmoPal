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
import com.dissertation.emopal.ui.screens.game.GameScreen
import com.dissertation.emopal.ui.screens.home.HomeScreen

@Composable
fun EmoPalApp(
    navController: NavHostController = rememberNavController()
) {
    /**
     * Navigate back to the Home Screen lambda function
     */
    val navigateBackToHome: () -> Unit = {
        navController.navigate(Routes.HOME.name) {
            popUpTo(Routes.HOME.name) {
                inclusive = true
            }
        }
    } // End of Lambda

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
                    // Diary Button Event //
                    onDiaryButtonClicked = {
                        navController.navigate(Routes.DIARY.name)
                    },
                    // Play Button Event //
                    onPlayButtonClicked = {
                        navController.navigate(Routes.PLAY.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        // TODO: Change padding to Resource Value Dimensions Example -> (R.dimen.padding)
                        .padding(16.dp)
                )
            }
            // TODO: Refactor the below to avoid code repetition
            composable(route = Routes.DIARY.name) {
                DiaryScreen(onBackButtonClicked = navigateBackToHome)
            } // End of Diary Screen

            composable(route = Routes.PLAY.name) {
                GameScreen(onBackButtonClicked = navigateBackToHome)
            } // End of Game Screen
            // TODO: Add the other screens below
        }
    } // End of Scaffold lambda
} // End of EmoPalApp Composable
