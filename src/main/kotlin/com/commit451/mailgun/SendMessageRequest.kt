package com.commit451.mailgun

import okhttp3.MultipartBody



/**
 * Send a message with the definition being this request. Use the [Builder] to create one
 */
class SendMessageRequest internal constructor() {

    internal var from: Contact? = null
    internal var to: List<Contact>? = null
    internal var cc: List<Contact>? = null
    internal var bcc: List<Contact>? = null
    internal var text: String? = null
    internal var html: String? = null
    internal var subject: String? = null
    internal var attachments: List<Attachment>? = null
    internal var inlineAttachments: List<Attachment>? = null
    internal var template: String? = null
    internal var templateVariables: Map<String, String>? = null

    internal fun toMultipartBody(): MultipartBody {
        val bodyBuilder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPartIfNotNull("from", from.toString())
                .addFormDataPartIfNotNull("subject", subject)
                .addFormDataPartIfNotNull("text", text)
                .addFormDataPartIfNotNull("html", html)
        to?.forEach {
            bodyBuilder.addFormDataPart("to", it.toString())
        }
        cc?.forEach {
            bodyBuilder.addFormDataPart("cc", it.toString())
        }
        bcc?.forEach {
            bodyBuilder.addFormDataPart("bcc", it.toString())
        }
        attachments?.forEach {
            bodyBuilder.addFormDataPart("attachment", it.fileName, it.requestBody)
        }
        inlineAttachments?.forEach {
            bodyBuilder.addFormDataPart("inline", it.fileName, it.requestBody)
        }
        template?.let {
            bodyBuilder.addFormDataPart("template", it)
            // Cant add template variables if no template is provided
            templateVariables?.forEach { (k, v) ->
                bodyBuilder.addFormDataPart("v:$k", v)
            }
        }
        return bodyBuilder.build()
    }

    class Builder(from: Contact) {

        private val request = SendMessageRequest()

        init {
            request.from = from
        }

        fun to(to: List<Contact>?): Builder {
            request.to = to
            return this
        }

        fun cc(cc: List<Contact>?): Builder {
            request.cc = cc
            return this
        }

        fun bcc(bcc: List<Contact>?): Builder {
            request.bcc = bcc
            return this
        }

        fun text(text: String?): Builder {
            request.text = text
            return this
        }

        fun html(html: String?): Builder {
            request.html = html
            return this
        }

        fun subject(subject: String?): Builder {
            request.subject = subject
            return this
        }

        fun template(template: String?) : Builder {
            request.template = template
            return this
        }

        fun templateVariables(templateVars: Map<String, String>) : Builder {
            request.templateVariables = templateVars
            return this
        }

        fun attachments(attachments: List<Attachment>): Builder {
            request.attachments = attachments
            return this
        }

        fun inlineAttachments(attachments: List<Attachment>): Builder {
            request.inlineAttachments = attachments
            return this
        }

        fun build(): SendMessageRequest {
            if (request.text == null && request.html == null) {
                throw Exception("Need to specify text or html parameter")
            }
            return request
        }
    }
}