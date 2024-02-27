package com.dissertation.emopal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dissertation.emopal.ui.theme.EmoPalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmoPalTheme {
                setContent{
                    /***
                     * This is the main entry point for the app
                     */
                    EmoPalApp()
                }
            }
        }
    }
}


