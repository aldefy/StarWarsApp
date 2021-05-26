package com.star.wars.common

interface HttpsTransformer {
    fun toHttpUrl(url: HttpUrl):HttpUrl
}

class HttpsTransformerImpl : HttpsTransformer {
    override fun toHttpUrl(url: HttpUrl):HttpUrl {
        return url.addHttps()
    }
}
