package com.commit451.mailgun

import org.junit.Assert
import org.junit.Test


class ApiTests {

    companion object {
        fun createMailgun(): Mailgun {
            return Mailgun.Builder("mail.example.com", "12345")
                    .build()
        }
    }

    @Test
    fun jsonResponseTest() {

        val mailgun = createMailgun()

        val from = Contact("blah@blah.com", "blah")
        val requestBuilder = SendMessageRequest.Builder(from)
        val to = mutableListOf<Contact>()
        to.add(Contact("jim@aol.com", "jim"))
        requestBuilder.to(to)
        val response = mailgun.sendMessage(requestBuilder.build())
                .blockingGet()
        Assert.assertNotNull(response)
    }
}