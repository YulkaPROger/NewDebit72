package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AutoFromNumber(
    @SerialName("Владелец") val owner: String,
    @SerialName("ТранспортныеСредстваДолжника") val tsOwner: String,
    @SerialName("ГосномерТС") val gosNumber: String,
    @SerialName("ТСАрестован") val arrested: Boolean,
    @SerialName("ДатаАрестаТС") val dateArrested: String,
    @SerialName("МестоХранения") val storage: String,
    @SerialName("СуммаПоОценкеТС") val sum: String,
    @SerialName("СтатусРеализацииТС") val stateRealised: String,
    @SerialName("КомментарииАвтомобили") val comment: String,
    @SerialName("ИПпоКоторомуАрестованоТС") val ipArrested: String,
    @SerialName("ТСРеализован") val tsRealised: Boolean,
    @SerialName("Адрес") val address: String,
    @SerialName("ИП") val ip: List<Ip> = emptyList(),
) {
    @Serializable
    data class Ip(
        @SerialName("Номер") val number: String,
        @SerialName("РегНомерИП") val regNumber: String,
        @SerialName("ОбщаяСуммаДолга") val totalDebt: String,
        @SerialName("ОстатокДолга") val balanceOwed: String,
        @SerialName("КодыВКСП") val rosp: String,
        @SerialName("СПИ") val spi: String,
        @SerialName("Взыскатель") val claimant: String,
        @SerialName("Адрес") val address: String,
    )
}