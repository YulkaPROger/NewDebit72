package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneralInformation(
    @SerialName("Количество") val count: String,
    @SerialName("ОстатокДолга") val balanceOwed: String,
    @SerialName("ОбщаяСуммаДолга") val totalDebt: String,
    @SerialName("СуммаПошлины") val amountOfDuty: String,
    @SerialName("СуммаЮрУслуг") val amountOfLegalServices: String,
    @SerialName("СуммаПени") val amountPenalty: String,
    @SerialName("СуммаВзысканияЖКУ") val amountOfCommunalServices: String
)