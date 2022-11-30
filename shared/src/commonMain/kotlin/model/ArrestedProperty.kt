package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArrestedProperty(
    @SerialName("Имущество") val propertyDebtor: String,
    @SerialName("СуммаПоОценке") val sum: String,
    @SerialName("ДатаОписиАреста") val dateArrested: String,
    @SerialName("Должник") val debtor: String,
    @SerialName("строкаПоиска") val searchLine: String,
    @SerialName("строкаИП") val ipClaimant: String,
)