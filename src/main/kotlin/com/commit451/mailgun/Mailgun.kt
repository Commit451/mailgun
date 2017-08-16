package com.commit451.mailgun

import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

class Mailgun private constructor(private val domain: String,
                                  private val apiKey: String,
                                  initialClientBuilder: OkHttpClient.Builder?) {

    companion object {
        private const val BASE_URL = "https://api.mailgun.net/v3/"
    }

    private val moshi: Moshi
    private val okHttpClient: OkHttpClient

    init {
        val clientBuilder = initialClientBuilder ?: OkHttpClient.Builder()

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

    fun sendMessage(sendMessageRequest: SendMessageRequest): Single<SendMessageResponse> {
        val requestBuilder = Request.Builder()
                .url("$BASE_URL$domain/messages")
                .post(sendMessageRequest.toMultipartBody())
        val call = okHttpClient.newCall(requestBuilder.build())
        return Single.defer {
            val response = call.execute()
            val body = response.body()?.source()
            if (body != null) {
                val typedResponse = moshi.adapter<SendMessageResponse>(SendMessageResponse::class.java).fromJson(body)
                Single.just(typedResponse)
            } else {
                throw Exception("Fail. Needs to be a better exception")
            }
        }
    }

    class Builder(private val domain: String, private val apiKey: String) {

        private var okHttpBuilder: OkHttpClient.Builder? = null

        fun okHttpClientBuilder(okHttpClientBuilder: OkHttpClient.Builder?): Builder {
            this.okHttpBuilder = okHttpClientBuilder
            return this
        }

        fun build(): Mailgun {
            return Mailgun(domain, apiKey, this.okHttpBuilder)
        }
    }
}