package com.commit451.mailgun

import com.google.common.io.Resources

@Suppress("UnstableApiUsage")
object Util {

    fun getFileText(fileName: String): String {
        val url = Resources.getResource(fileName)
        return Resources.toString(url, Charsets.UTF_8)
    }
}