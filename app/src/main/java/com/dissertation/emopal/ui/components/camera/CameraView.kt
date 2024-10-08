package com.dissertation.emopal.ui.components.camera

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.dissertation.emopal.util.rotateBitmap
import java.util.concurrent.Executor

/**
 * CameraView Composable which contains the camera preview and the switch camera button.
 * @Author: A. La Fauci
 * @Date: 02/03/2024
 */

@Composable
fun CameraView(
    onBackButtonClicked: () -> Unit,
    onTakePhoto: (Bitmap) -> Unit,
    isButtonVisible: Boolean,
    shouldTakePicture: Boolean,
) {
    // The current application context
    val applicationContext = LocalContext.current
    val previewView: PreviewView = remember { PreviewView(applicationContext) }
    val cameraController = remember { LifecycleCameraController(applicationContext) }
    val lifecycleOwner = LocalLifecycleOwner.current
    cameraController.bindToLifecycle(lifecycleOwner)
    cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    previewView.controller = cameraController
    
    var isLoading by remember { mutableStateOf(false) }


    fun takePicture(
        controller: LifecycleCameraController,
    ) {
        isLoading = true

        val executor: Executor = ContextCompat.getMainExecutor(applicationContext)

        controller.takePicture(
            executor,
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    // Creating the bitmap and rotating it
                    val bitmap = image.toBitmap().rotateBitmap(image.imageInfo.rotationDegrees)
                    onTakePhoto(bitmap)
                    // Close the image to free up the resources
                    image.close()
                    isLoading = false
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Error taking picture", exception)
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

        if (isButtonVisible) {
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
                // CAPTURE BUTTON //
                IconButton(
                    onClick = {
                        takePicture(
                            controller = cameraController,
                            // Delegating the save picture to the View Model
//                        onTakePhoto = cameraViewModel::savePicture,
//                        onPictureTaken = { onBackButtonClicked() }
                        )
                    },
                    modifier = Modifier.size(116.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = "Take Picture",
                        modifier = Modifier
                            .size(116.dp)
                            .background(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.shapes.small
                            )
                            .padding(16.dp)
                    )
                }
            }

        }

    } // End of Box

    // LaunchedEffect to trigger the camera to take a picture when the flag is set to true
    LaunchedEffect(shouldTakePicture) {
        if (shouldTakePicture) {
            takePicture(controller = cameraController)
        }
    }

    // Loading Indicator
    if (isLoading) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

} // End of Composable

