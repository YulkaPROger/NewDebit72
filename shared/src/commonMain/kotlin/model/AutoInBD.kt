package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AutoInBD(
    @SerialName("Владелец") val owner: String,
    @SerialName("ТранспортныеСредстваДолжника") val tsDebtor: String,
    @SerialName("МодельТС") val modelTs: String,
    @SerialName("ГосномерТС") val gosNumber: String,
    @SerialName("ТСАрестован") val arrested: Boolean,
    @SerialName("ДатаАрестаТС") val dateArrested: String,
    @SerialName("МестоХранения") val repository: String,
    @SerialName("СуммаПоОценкеТС") val sumTS: String,
    @SerialName("СтатусРеализацииТС") val stateRealizedTS: String,
    @SerialName("КомментарииАвтомобили") val comment: String,
    @SerialName("ИПпоКоторомуАрестованоТС") val arrestedIp: String,
    @SerialName("ТСРеализован") val realized: Boolean,
    @SerialName("Адрес") val address: String,
    @SerialName("ипДолжника") val ipClaimant: String,
)
