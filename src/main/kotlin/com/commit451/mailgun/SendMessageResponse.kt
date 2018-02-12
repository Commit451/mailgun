package com.commit451.mailgun

import com.squareup.moshi.Json

/**
 * The response from the server after sending a message
 */
class SendMessageResponse {

    @field:Json(name = "id")
    var id: String? = null
    @field:Json(name = "message")
    var message: String? = null
}