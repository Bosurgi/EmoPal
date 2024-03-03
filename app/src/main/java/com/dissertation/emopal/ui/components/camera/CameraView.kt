package com.dissertation.emopal.ui.components.camera

import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.SwitchCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

/**
 * CameraView Composable which contains the camera preview and the switch camera button.
 * @Author: A. La Fauci
 * @Date: 02/03/2024
 */

@Composable
fun CameraView(onBackButtonClicked: () -> Unit) {
    // The current application context
    val applicationContext = LocalContext.current
    val previewView: PreviewView = remember { PreviewView(applicationContext) }
    val cameraController = remember { LifecycleCameraController(applicationContext) }
    val lifecycleOwner = LocalLifecycleOwner.current
    cameraController.bindToLifecycle(lifecycleOwner)
    cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    previewView.controller = cameraController

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

        // TOP ROW FOR THE SWITCH CAMERA AND BACK BUTTON //
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // BACK ICON BUTTON //
            IconButton(onClick = { onBackButtonClicked() }, modifier = Modifier.size(64.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Diary"
                )
            } // End of Back Icon Button

            // SWITCH CAMERA ICON BUTTON //
            IconButton(
                onClick = {
                    cameraController.cameraSelector =
                        if (cameraController.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else {
                            CameraSelector.DEFAULT_BACK_CAMERA
                        }
                },
                modifier = Modifier
                    .size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SwitchCamera,
                    contentDescription = "Swap Camera"
                )
            } // End of Icon Button
        }

        // BOTTOM ROW FOR THE CAPTURE BUTTON //
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(128.dp)) {
                Icon(
                    imageVector = Icons.Default.Camera,
                    contentDescription = "Take Picture"
                )
            }
        }

    } // End of Box

} // End of Composable

