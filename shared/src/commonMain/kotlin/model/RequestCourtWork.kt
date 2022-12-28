package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCourtWork(
    @SerialName("Суд") val court: String,
    @SerialName("ЗапросКому") val requestTo: String,
    @SerialName("ДатаПодачиЗапроса") val dateRequest: String,
    @SerialName("Взыскатель") val claimant: String,
    @SerialName("ПредметЗапроса") val subject: String,
    @SerialName("ДатаПредоставленияОтвета") val dateResponse: String
    )