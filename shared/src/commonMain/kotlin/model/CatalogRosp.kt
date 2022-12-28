package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatalogRosp(
    @SerialName("Наименование") val name: String,
    @SerialName("Руководитель") val owner: String,
    @SerialName("eMail") val eMail: String,
    @SerialName("Телефоны") val phone: String,
)