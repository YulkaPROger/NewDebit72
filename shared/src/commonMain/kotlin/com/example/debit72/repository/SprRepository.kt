package com.example.debit72.repository

import com.example.debit72.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.FullSpr
import model.Spr

class SprRepository {

    suspend fun getAllSpr(): List<Spr> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.SPR
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }

    suspend fun getSprForLink(number: String): FullSpr {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.LINK_SPR
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
                parameters.append("link", number)
            }
        }
        return response.body()
    }

}