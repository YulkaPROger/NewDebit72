package com.example.debit72.repository

import com.example.debit72.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.ArrestedProperty
import model.OldArrestedAuto
import model.OldArrestedProperty

class ArrestedPropertyRepository {
    suspend fun getArrestedPropertyList(): List<ArrestedProperty> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.ARRESTED_PROPERTY
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }

    suspend fun getOldArrestedAutoList(): List<OldArrestedProperty> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.OLD_ARRESTED_PROPERTY
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }
}