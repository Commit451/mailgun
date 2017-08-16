package com.commit451.mailgun

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test


class ApiTests {

    companion object {
        fun createMailgun(baseUrl: String): Mailgun {
            return Mailgun.Builder("mail.example.com", "12345")
                    .baseUrl(baseUrl)
                    .build()
        }
    }

    @Test
    fun jsonResponseTest() {

        val server = MockWebServer()
        server.enqueue(MockResponse().setBody(Util.getFileText("sent-response.json")))
        server.start()

        val mailgun = createMailgun(server.url("").toString())

        val from = Contact("blah@blah.com", "blah")
        val requestBuilder = SendMessageRequest.Builder(from)
        val to = mutableListOf<Contact>()
        to.add(Contact("jim@aol.com", "jim"))
        requestBuilder.to(to)
        requestBuilder.text("Hi")

        val response = mailgun.sendMessage(requestBuilder.build())
                .blockingGet()

        Assert.assertNotNull(response)
        Assert.assertNotNull(response.id)
    }
}