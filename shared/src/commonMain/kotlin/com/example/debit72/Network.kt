package com.example.debit72

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Network {

    const val BASE_URL = "https://4315-91-194-113-1.eu.ngrok.io/"
    private var httpClient: HttpClient? = null

    fun getHttpClient(): HttpClient {
        if (httpClient == null) {
            httpClient = HttpClient() {
                install(Auth) {
                    basic {
                        credentials {
                            BasicAuthCredentials("AABBASOV", "12345678z") //впиши свое //
                        }
                    }
                }
                install(Logging) {
                    level = LogLevel.HEADERS
                    logger = object : Logger {
                        override fun log(message: String) {
                            Napier.v(tag = "Http Client", message = message)
                        }
                    }
                }
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
            }.also {
                initLogger()
            }
        }
        return httpClient as HttpClient
    }

}