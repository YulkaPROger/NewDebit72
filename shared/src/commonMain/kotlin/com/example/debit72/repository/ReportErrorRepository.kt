package com.example.debit72.repository

import com.example.debit72.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.ReportError

class ReportErrorRepository {
    suspend fun getInfo(): List<ReportError> {
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
}