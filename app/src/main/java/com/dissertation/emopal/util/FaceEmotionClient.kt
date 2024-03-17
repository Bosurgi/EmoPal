package com.dissertation.emopal.util

import android.graphics.Bitmap
import android.util.Base64
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


/**
 * This is the client that sends the image to the Google Cloud Vision API.
 * It sends the image and the feature type to the API and returns the response.
 * @Author: A. La Fauci
 * @Date: 16/03/2024
 */
class FaceEmotionClient(apiKey: String) {
    // The URL of the Google Cloud Vision API
    private val apiUrl = "https://vision.googleapis.com/v1/images:annotate?key=$apiKey"

    /**
     * High-level function that collects the image, the feature needed and the results to the API.
     * It also ensure to call the other functions to encode the image and create the request body.
     * See [encodeBitmapToBase64], [createRequestBody] and [sendRequest] for more details.
     * @param bitmap The image to be sent to the API.
     * @param featureType The type of feature to be detected in the image.
     * @param maxResults The maximum number of results to be returned by the API.
     * @return The response from the API.
     */
    fun annotateImage(bitmap: Bitmap, featureType: String, maxResults: Int): String {
        val imageContent = encodeBitmapToBase64(bitmap)
        val requestBody = createRequestBody(imageContent, featureType, maxResults)
        return sendRequest(requestBody)
    }

    /**
     * This function encodes the image to Base64 to be sent to the API.
     * @param bitmap The image to be encoded.
     * @return The Base64 encoded image.
     */
    private fun encodeBitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageBytes = outputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    /**
     * This function creates the request body to be sent to the API.
     * @param imageContent The Base64 encoded image.
     * @param featureType The type of feature to be detected in the image.
     * @param maxResults The maximum number of results to be returned by the API.
     */
    private fun createRequestBody(
        imageContent: String,
        featureType: String,
        maxResults: Int
    ): String {
        val requestsArray = JSONArray()
        val requestObject = JSONObject()
        val imageObject = JSONObject()
        val featureObject = JSONObject()

        imageObject.put("content", imageContent)
        featureObject.put("type", featureType)
        featureObject.put("maxResults", maxResults)

        requestObject.put("image", imageObject)
        requestObject.put("features", JSONArray().put(featureObject))

        requestsArray.put(requestObject)

        val requestBody = JSONObject()
        requestBody.put("requests", requestsArray)
        return requestBody.toString()
    }

    /**
     * This function sends the POST request to the API and returns the response.
     * @param requestBody The request body to be sent to the API.
     * @return The response from the API.
     */
    private fun sendRequest(requestBody: String): String {
        val url = URL(apiUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        val outputStream = OutputStreamWriter(connection.outputStream)
        outputStream.write(requestBody)
        outputStream.flush()

        val responseCode = connection.responseCode
        val response = if (responseCode == HttpURLConnection.HTTP_OK) {
            connection.inputStream.bufferedReader().use { it.readText() }
        } else {
            "Error: HTTP $responseCode"
        }

        connection.disconnect()
        return response
    }

}