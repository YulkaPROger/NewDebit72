package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OldArrestedAuto(
    @SerialName("МодельТС") val modelTs: String,
    @SerialName("ГосномерТС") val gosNumber: String,
    @SerialName("ДатаАрестаТС") val dateArrestedTs: String,
    @SerialName("МестоХранения") val storage: String,
    @SerialName("СуммаПоОценкеТС") val price: String,
    @SerialName("Владелец") val owner: String,
    @SerialName("ИП") val ip: List<OldIp>,
)

@Serializable
class OldArrestedProperty(
    @SerialName("ОНД") val property: String,
    @SerialName("СуммаПоОценке") val price: String,
    @SerialName("ДатаОписиАреста") val dateArrested: String,
    @SerialName("Должник") val owner: String,
    @SerialName("ИП") val ip: List<OldIp>,
)

@Serializable
data class OldIp(
    @SerialName("Взыскатель") val claimant: String,
    @SerialName("КодыВКСП") val rosp: String,
    @SerialName("РегНомерИП") val regNumberIp: String,
    @SerialName("Номер") val number: String,
    @SerialName("ОстатокДолга") val balanceOwed: String,
    @SerialName("СПИ") val spi: String,
)