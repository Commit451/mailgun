package com.commit451.mailgun

import com.squareup.moshi.Json

/**
 * The response from the server after sending a message
 */
data class SendMessageResponse(
        @Json(name = "id")
        val id: String? = null,
        @Json(name = "message")
        val message: String? = null
)