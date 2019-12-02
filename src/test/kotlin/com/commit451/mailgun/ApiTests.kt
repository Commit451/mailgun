package com.commit451.mailgun

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertNotNull
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
    fun `JSON response from server`() {

        val server = MockWebServer()
        server.enqueue(MockResponse().setBody(Util.getFileText("sent-response.json")))
        server.start()

        val mailgun = createMailgun(server.url("").toString())

        val from = Contact("blah@blah.com", "blah")
        val requestBuilder = SendMessageRequest.Builder(from)
        val to = mutableListOf<Contact>()
        to.add(Contact("jim@aol.com", "jim"))

        val attachment = Attachment(
                fileName = "text.txt",
                requestBody = "This is in a text file".toRequestBody("text/plain".toMediaTypeOrNull())
        )
        val attachments = listOf(attachment)

        requestBuilder.to(to)
                .text("Hi")
                .attachments(attachments)

        val response = mailgun.sendMessage(requestBuilder.build())
                .blockingGet()

        assertNotNull(response)
        assertNotNull(response.id)
        assertNotNull(response.message)
    }
}