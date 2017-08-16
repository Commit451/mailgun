package com.commit451.mailgun

import okhttp3.MultipartBody

internal fun MultipartBody.Builder.addFormDataPartIfNotNull(name: String, param: String?): MultipartBody.Builder {
    if (param != null) {
        addFormDataPart(name, param)
    }
    return this
}