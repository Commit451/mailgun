package com.commit451.mailgun

import okhttp3.RequestBody


class Attachment(val name: String, val file: RequestBody)