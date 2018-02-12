package com.commit451.mailgun

import okhttp3.RequestBody

/**
 * The attachment
 */
class Attachment(val name: String, val file: RequestBody)