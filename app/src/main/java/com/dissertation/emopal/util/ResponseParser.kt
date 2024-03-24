package com.dissertation.emopal.util

import org.json.JSONObject

/**
 * @Author: A. La Fauci
 * @Date: 24/03/2024
 */
class ResponseParser {

    companion object {
        /**
         * Function to parse the response from the Vision API.
         * @param response: the response from the Vision API.
         * @return a string containing the emotion detected.
         */
        fun parseResponse(response: String): String {
            try {
                var emotion = "Unknown"

                // Parse the JSON response
                val jsonResponse = JSONObject(response)
                val responsesArray = jsonResponse.getJSONArray("responses")
                for (i in 0 until responsesArray.length()) {
                    val responseObj = responsesArray.getJSONObject(i)
                    val faceAnnotations = responseObj.getJSONArray("faceAnnotations")
                    for (j in 0 until faceAnnotations.length()) {
                        val annotation = faceAnnotations.getJSONObject(j)
                        // Emotion Likelihood by category
                        val joyLikelihood = annotation.optString("joyLikelihood")
                        val sorrowLikelihood = annotation.optString("sorrowLikelihood")
                        val angerLikelihood = annotation.optString("angerLikelihood")
                        val surpriseLikelihood = annotation.optString("surpriseLikelihood")

                        // Updating the emotion based on the likelihood of the detected emotions
                        emotion =
                            if (joyLikelihood == "VERY_LIKELY" || joyLikelihood == "LIKELY" || joyLikelihood == "POSSIBLE") {
                                "happy"
                            } else if (sorrowLikelihood == "VERY_LIKELY" || sorrowLikelihood == "LIKELY" || sorrowLikelihood == "POSSIBLE") {
                                "sad"
                            } else if (angerLikelihood == "VERY_LIKELY" || angerLikelihood == "LIKELY" || angerLikelihood == "POSSIBLE") {
                                "angry"
                            } else if (surpriseLikelihood == "VERY_LIKELY" || surpriseLikelihood == "LIKELY" || surpriseLikelihood == "POSSIBLE") {
                                "surprised"
                            } else {
                                "neutral"
                            }
                    }
                }
                return emotion
            } catch (e: Exception) {
                return "Unknown"
            }
        }
    }
}