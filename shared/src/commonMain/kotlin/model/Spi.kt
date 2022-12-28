package model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Spi(
    @SerialName("Наименование") val name: String,
    @SerialName("РОСП") val rosp: String,
    @SerialName("Телефон") val phone: String,
)
