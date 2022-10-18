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

    private var httpClient: HttpClient? = null

    const val BASE_URL = "http://109.194.162.125/debit72/hs/debit72/"
    const val INFO = "PreviewInfoV2"

    const val KEY_API = "V784hfdsjUUreregTgv\$fgjhgkQhkNonStopfdgfJJonAV039MAS0714"
    const val FIO = "Колосов Виктор Константинович"
    const val BIRTHDAY = "14.04.1955 00:00:00"

    fun getHttpClient(): HttpClient {
        if (httpClient == null) {
            httpClient = HttpClient() {
                install(Auth) {
                    basic {
                        credentials {
                            BasicAuthCredentials("administrator", "Fiona2005") //впиши свое //
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