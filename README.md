# mailgun

Mailgun API access in Kotlin. Uses OkHttp, Moshi, and RxJava under the hood. Android 5.0/Java 8 compatible.

[![Build Status](https://travis-ci.org/Commit451/mailgun.svg?branch=master)](https://travis-ci.org/Commit451/mailgun) [![](https://jitpack.io/v/Commit451/mailgun.svg)](https://jitpack.io/#Commit451/mailgun)

## Gradle Dependency
Add the jitpack url to the project:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
then, in your app `build.gradle`
```groovy
dependencies {
    implementation "com.github.Commit451:mailgun:latest.version.here"
}
```

## Usage
All access to the API is done via the `Mailgun` class. You can create one like so:
```kotlin
val mailgun = Mailgun.Builder("mail.domain.com", "your_api_key")
    .build()
```

### Send Message
To send a message (email):
```kotlin
val from = Contact("blah@blah.com", "blah")

val to = mutableListOf<Contact>()
to.add(Contact("jim@example.com", "jim"))
val attachment = Attachment(
    fileName = "text.txt",
    requestBody = RequestBody.create(MediaType.parse("text/plain"), "This is in a text file")
)
val attachments = listOf(attachment)

val requestBuilder = SendMessageRequest.Builder(from)
    .to(to)
    .text("Hi")
    .attachments(attachments)

val response = mailgun.sendMessage(requestBuilder.build())
        .blockingGet()
```

## Reference
- https://gist.github.com/mescortes/f34ca5dcb9386adc71f352e92454bf05
- http://mailgun-documentation.readthedocs.io/en/latest/api_reference.html


License
--------

    Copyright 2019 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.