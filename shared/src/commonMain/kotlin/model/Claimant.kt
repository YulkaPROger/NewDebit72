package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Claimant(
    @SerialName("Наименование") val name: String,
    @SerialName("Адрес") val address: String,
    @SerialName("Банк") val bank: String,
    @SerialName("ИНН") val inn: String,
    @SerialName("КПП") val kpp: String,
    @SerialName("ОГРН") val ogrn: String,
    @SerialName("РС") val rs: String,
    @SerialName("КС") val ks: String,
    @SerialName("ПолноеНаименование") val fullName: String,
    @SerialName("Директор") val owner: String,
    @SerialName("Телефон") val phone: String,
    @SerialName("EMail") val email: String,
    @SerialName("БИК") val bic: String,
    )