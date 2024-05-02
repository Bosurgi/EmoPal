package com.dissertation.emopal

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dissertation.emopal.ui.theme.EmoPalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Asking user for permission to use the defined permissions needed
        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 0)
        }
        setContent {
            EmoPalTheme {
                    /***
                     * This is the main entry point for the app
                     */
                    EmoPalApp()
            }
        }
    }

    /**
     * Check if the app has the necessary permissions to run.
     */
    private fun hasPermissions(): Boolean {
        return PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Companion object to define the permissions needed for the application to run.
     * In this case the app needs the camera and internet permissions.
     */
    companion object {
        private val PERMISSIONS = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.INTERNET,
        )
    }
}


