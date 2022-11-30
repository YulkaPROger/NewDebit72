package com.example.debit72.repository

import com.example.debit72.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.AutoInBD

class AutoRepository {
    suspend fun getAutoList(): List<AutoInBD> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.AUTO
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }
}