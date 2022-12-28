package com.example.debit72

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.ThreadLocal


@ThreadLocal
object Network {

    private var httpClient: HttpClient? = null

    const val BASE_URL = "http://109.194.162.125/debit72/hs/debit72/"
    const val INFO = "PreviewInfoV2"
    const val ALL_IP = "ipv2"
    const val LINK_IP = "linkIpv2"
    const val SPR = "spr"
    const val LINK_SPR = "spr_link_v2"
    const val CLAIMANTS = "claimants"
    const val CLAIMANTS_ON_ROSP = "claimants_on_the_rosp_v2"
    const val AUTO = "auto_v2"
    const val ARRESTED_AUTO = "AutoAresstedV2"
    const val ARRESTED_PROPERTY = "arrested_property_v2"
    const val SIR = "sir_v2"
    const val AUTO_NUMBER = "auto_number_v2"
    const val ERROR_REPORT = "report_error_v2"
    const val REQUEST_COURT_WORK = "request_court_work_v2"
    const val CATALOG_ROSP = "catalog_rosp_v2"
    const val CATALOG_SPI = "catalog_spi_v2"
    const val CATALOG_COURT = "catalog_court_v2"
    const val TASKS = "tasks_v2"


    @Deprecated("старая реализация")
    const val OLD_ARRESTED_AUTO = "arestedTS"

    @Deprecated("старая реализация")
    const val OLD_ARRESTED_PROPERTY = "arestedProperty"

    const val KEY_API = "V784hfdsjUUreregTgv\$fgjhgkQhkNonStopfdgfJJonAV039MAS0714"
    const val FIO = "Колосов Виктор Константинович"
    const val BIRTHDAY = "14.04.1955 00:00:00"

    fun getHttpClient(): HttpClient {
        if (httpClient == null) {
            httpClient = HttpClient(CIO) {
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
                        encodeDefaults = true
                    })
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 500000
                    socketTimeoutMillis = 500000
                    connectTimeoutMillis = 500000
                }

            }.also {
                initLogger()
            }
        }
        return httpClient as HttpClient
    }

}