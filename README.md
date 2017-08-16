# mailgun-kotlin

Mailgun API access written in Kotlin. Uses OkHttp, Moshi, and RxJava under the hood. Android/Java 7 compatible.

[![Build Status](https://travis-ci.org/Commit451/mailgun-kotlin.svg?branch=master)](https://travis-ci.org/Commit451/mailgun-kotlin) [![](https://jitpack.io/v/Commit451/mailgun-kotlin.svg)](https://jitpack.io/#Commit451/mailgun-kotlin)

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
    compile "com.github.Commit451:mailgun-kotlin:latest.version.here"
}
```

## Usage
//TODO

## Reference
- https://gist.github.com/mescortes/f34ca5dcb9386adc71f352e92454bf05
- http://mailgun-documentation.readthedocs.io/en/latest/api_reference.html


License
--------

    Copyright 2017 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.