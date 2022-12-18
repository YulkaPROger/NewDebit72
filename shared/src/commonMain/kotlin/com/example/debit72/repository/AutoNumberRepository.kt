package com.example.debit72.repository

import com.example.debit72.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.AutoFromNumber

class AutoNumberRepository {

    suspend fun getAuto(number: String): List<AutoFromNumber> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.AUTO_NUMBER
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
                parameters.append("StateNumber", number)
            }
        }
        return response.body()
    }
}