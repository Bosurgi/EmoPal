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
import androidx.navigation.navigation
import com.dissertation.emopal.routes.Routes
import com.dissertation.emopal.ui.components.camera.CameraView
import com.dissertation.emopal.ui.screens.diary.DiaryScreen
import com.dissertation.emopal.ui.screens.home.HomeScreen
import com.dissertation.emopal.ui.screens.play.Level
import com.dissertation.emopal.ui.screens.play.PlayScreen

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

    val navigateBackToPlay: () -> Unit = {
        navController.navigate(Routes.NESTED_PLAY.name) {
            popUpTo(Routes.NESTED_PLAY.name) {
                inclusive = true
            }
        }
    } // End of Lambda

    val navigateBackToDiary: () -> Unit = {
        navController.navigate(Routes.NESTED_DIARY.name) {
            popUpTo(Routes.NESTED_DIARY.name) {
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
            } // End of Home Screen for NavGraphBuilder

            // DIARY SCREEN NAVIGATION GRAPH //
            navigation(startDestination = Routes.DIARY.name, route = Routes.NESTED_DIARY.name) {
                composable(route = Routes.DIARY.name) {
                    DiaryScreen(onBackButtonClicked = navigateBackToHome, onTakePictureClicked = {
                        navController.navigate(Routes.CAMERA.name)
                    })
                }
                // TODO: Add Back button function to navigate back to the Diary Screen
                composable(route = Routes.CAMERA.name) {
                    CameraView(navigateBackToDiary)
                }
            } // End of Nested Navigation Graph for Diary Screen

            // Nested Navigation Graph from PLAY Screen //
            navigation(
                startDestination = Routes.PLAY.name,
                route = Routes.NESTED_PLAY.name,
            ) {

                // PLAY SCREEN //
                composable(route = Routes.PLAY.name) {
                    PlayScreen(
                        onBackButtonClicked = navigateBackToHome,
                        onLevelClicked = { levelRoute ->
                            navController.navigate(levelRoute)
                        })
                }
                // LEVEL SCREENS //

                composable(route = Routes.LEVEL1.name) {
                    /* TODO: Implement Actual mini-games for each Level */
                    Level(level = "1", onBackButtonClicked = navigateBackToPlay)
                }

                composable(route = Routes.LEVEL2.name) {
                    /* TODO: Implement Actual mini-games for each Level */
                    Level(level = "2", onBackButtonClicked = navigateBackToPlay)
                }

                composable(route = Routes.LEVEL3.name) {
                    /* TODO: Implement Actual mini-games for each Level */
                    Level(level = "3", onBackButtonClicked = navigateBackToPlay)
                }
            } // End of Nested Navigation Graph for Play Screen

            // TODO: Add the other screens below
        }
    } // End of Scaffold lambda
} // End of EmoPalApp Composable
