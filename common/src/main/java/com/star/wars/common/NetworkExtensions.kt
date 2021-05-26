package com.star.wars.common

typealias HttpUrl = String

fun HttpUrl.addHttps(): HttpUrl {
    var string = ""
    string = if (this.contains("https")) {
        this
    } else {
        this.replace("http", "https")
    }
    return string
}
