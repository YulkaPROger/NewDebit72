package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullSpr(
    @SerialName("ПериодДолгаПо") val periodPo: String,
    @SerialName("ПериодДолгаС") val periodS: String,
    @SerialName("СуммаДолга") val debt: String,
    @SerialName("СуммаЖКУИПени") val sumOfZHKU: String,
    @SerialName("СуммаПени") val penalties: String,
    @SerialName("СуммаПошлины") val duty: String,
)
