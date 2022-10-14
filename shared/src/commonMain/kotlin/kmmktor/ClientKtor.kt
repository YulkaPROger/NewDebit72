package kmmktor

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ClientKtor {

    private val client = HttpClient()
    private val homeDirectory = "http://109.194.162.125/debit72/hs/debit72/"

    suspend fun greeting(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
}