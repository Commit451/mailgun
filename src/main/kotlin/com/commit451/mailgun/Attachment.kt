package com.commit451.mailgun

import okhttp3.RequestBody

/**
 * The attachment to send in the email
 */
class Attachment(val fileName: String, val requestBody: RequestBody)