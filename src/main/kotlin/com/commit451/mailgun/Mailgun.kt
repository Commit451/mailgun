package com.commit451.mailgun

import com.commit451.ehhttp.toSingle
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * The Mailgun instance. Use the [Builder] to create one
 */
class Mailgun private constructor(builder: Mailgun.Builder) {

    companion object {
        private const val BASE_URL = "https://api.mailgun.net/v3/"
    }

    private val domain: String
    private val apiKey: String
    private val moshi: Moshi
    private val okHttpClient: OkHttpClient
    private val baseUrl: String

    init {
        domain = builder.domain
        apiKey = builder.apiKey
        baseUrl = builder.baseUrl ?: BASE_URL
        val clientBuilder = builder.okHttpBuilder ?: OkHttpClient.Builder()

        clientBuilder.interceptors().add(0, Interceptor { chain ->
            val credentials = Credentials.basic("api", apiKey)
            val request = chain.request().newBuilder()
                    .header("Authorization", credentials)
                    .build()
            chain.proceed(request)
        })
        okHttpClient = clientBuilder.build()

        moshi = Moshi.Builder()
                .build()
    }

    /**
     * Send a message (email) with the specified request
     */
    fun sendMessage(sendMessageRequest: SendMessageRequest): Single<SendMessageResponse> {
        val requestBuilder = Request.Builder()
                .url("$baseUrl$domain/messages")
                .post(sendMessageRequest.toMultipartBody())
        val call = okHttpClient.newCall(requestBuilder.build())
        return call.toSingle()
                .flatMap { response ->
                    val body = response.body()?.source()
                    if (body != null) {
                        val typedResponse = moshi.adapter<SendMessageResponse>(SendMessageResponse::class.java).fromJson(body)
                        Single.just(typedResponse)
                    } else {
                        throw NullPointerException("Response body was null")
                    }
                }
    }

    /**
     * Build [Mailgun] instances
     */
    class Builder(internal val domain: String, internal val apiKey: String) {

        internal var okHttpBuilder: OkHttpClient.Builder? = null
        internal var baseUrl: String? = null

        /**
         * Set the custom [OkHttpClient.Builder] which will be used for network calls
         */
        fun okHttpClientBuilder(okHttpClientBuilder: OkHttpClient.Builder?): Builder {
            this.okHttpBuilder = okHttpClientBuilder
            return this
        }

        /**
         * Set the base url, should only need to be used for internal tests
         */
        fun baseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        /**
         * Create a [Mailgun] instance
         */
        fun build(): Mailgun {
            return Mailgun(this)
        }
    }
}