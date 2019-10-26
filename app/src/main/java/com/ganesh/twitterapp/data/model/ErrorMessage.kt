package com.ganesh.twitterapp.data.model

object ErrorMessage {

    fun getMessage(message: String): String {

        var errorMessage = message

        when (message) {

            "HTTP 404 Not Found" -> {
                errorMessage = "City Name Not found"
            }

        }

        return errorMessage
    }
}