package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sir(
    @SerialName("Взыскатель") val claimant: String,
    @SerialName("СудРассматривающийЗаявление") val court: String,
    @SerialName("ФИОСудьи") val fioReferee: String,
    @SerialName("НомерДела") val number: String,
    @SerialName("НаКогоПодаемВСуд") val naKogo: String,
    @SerialName("ДатаБеседы") val dateConversations: String,
    @SerialName("ДатаЗаседания") val dateMeetings: String,
    @SerialName("Адрес") val address: String,
    @SerialName("ПериодДолгаС") val periodTo: String,
    @SerialName("ПериодДолгаПо") val periodFrom: String,
    @SerialName("СуммаДолга") val totalDebt: String,
    @SerialName("СуммаПени") val penalties: String,
    @SerialName("СуммаПошлины") val duty: String,
    @SerialName("СуммаЮрУслуг") val legalServices: String,
    )
