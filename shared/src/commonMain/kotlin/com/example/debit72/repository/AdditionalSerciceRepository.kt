package com.example.debit72.repository

import com.example.debit72.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.*

class AdditionalSerciceRepository {
    suspend fun reportErrorList(): List<ReportError> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.ERROR_REPORT
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }

    suspend fun requestCourtWorkList(): List<RequestCourtWork> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.REQUEST_COURT_WORK
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }

    suspend fun catalogRospList(): List<CatalogRosp> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.CATALOG_ROSP
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }

    suspend fun spiList(search: String): List<Spi> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.CATALOG_SPI
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
                parameters.append("search", search)
            }
        }
        return response.body()
    }

    suspend fun courts(): List<Court> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.CATALOG_COURT
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }

    suspend fun tasks(): List<Task> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.TASKS
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }
}