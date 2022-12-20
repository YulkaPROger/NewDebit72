package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportError(
    @SerialName("НомерСтроки") val numberRow: String,
    @SerialName("СутьОписки") val essenceError: String,
    @SerialName("ДатаПодачиХодатайства") val date: String,
    @SerialName("СтатусИсполнения") val state: String,
    @SerialName("КомментарийОписки") val comment: String,
    @SerialName("Номер") val numberIpInside: String,
    @SerialName("Взыскатель") val claimant: String,
    @SerialName("Должник") val debtor: String,
    @SerialName("СПИ") val spi: String,
    @SerialName("РОСП") val rosp: String,
    @SerialName("НомерИД") val numberID: String,
    @SerialName("НомерИП") val numberIP: String,
    @SerialName("СтатусПроизводства") val stateIP: String,

)