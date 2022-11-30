package com.example.debit72.repository

import com.example.debit72.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.ArrestedAuto
import model.OldArrestedAuto

class ArrestedAutoRepository {
    suspend fun getArrestedAutoList(): List<ArrestedAuto> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.ARRESTED_AUTO
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }

    suspend fun getOldArrestedAutoList(): List<OldArrestedAuto> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.OLD_ARRESTED_AUTO
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }
}