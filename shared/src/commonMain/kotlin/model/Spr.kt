package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListSpr(
    val listIP: List<Spr>
)

@Serializable
data class Spr(
    @SerialName("НаКогоПодаемВСуд") val naKogo: String,
    @SerialName("Взыскатель") val claimant: String,
    @SerialName("ДатаПодачиЗаявления") val submissionDate: String,
    @SerialName("Суд") val court: String,
    @SerialName("СудРассматривающийЗаявление") val currentCourt: String,
    @SerialName("Адрес") val address: String,
    @SerialName("АдресДляПоиска") val addressForSearch: String,
    @SerialName("Номер") val number: String
)
