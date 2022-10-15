package kmmktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import model.GeneralInformation

class ClientKtor {

    private val client = HttpClient()
    private val homeDirectory = "http://109.194.162.125/debit72/hs/debit72/"
    private val info = "PreviewInfoV2"

    private val keyApi = "V784hfdsjUUreregTgv\$fgjhgkQhkNonStopfdgfJJonAV039MAS0714"
    private val aPIkey = "Колосов Виктор Константинович"
    private val birthday = "14.04.1955 00:00:00"
    suspend fun generalInformation(): GeneralInformation {
        val response = client.get(homeDirectory + info){
            url {
                parameters.append("APIkey", keyApi)
                parameters.append("Birthday", birthday)
            }
        }
        return response.body<GeneralInformation>()
    }

    suspend fun greeting(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
}

