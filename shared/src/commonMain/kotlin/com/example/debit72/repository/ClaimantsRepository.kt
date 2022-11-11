package com.example.debit72.repository

import com.example.debit72.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import model.Claimant
import model.ClaimantsOnTheRosp

class ClaimantsRepository {
    suspend fun getClaimantsList(): List<Claimant> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.CLAIMANTS
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }

    suspend fun getClaimantsOnRospList(): List<ClaimantsOnTheRosp> {
        val response: HttpResponse = Network.getHttpClient().get(
            Network.BASE_URL + Network.CLAIMANTS_ON_ROSP
        ) {
            method = HttpMethod.Get
            url {
                parameters.append("APIkey", Network.KEY_API)
            }
        }
        return response.body()
    }
}