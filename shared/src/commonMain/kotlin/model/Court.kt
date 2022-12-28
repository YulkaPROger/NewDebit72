package model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Court(
    @SerialName("Код") val code: String,
    @SerialName("Наименование") val name: String,
    @SerialName("Адрес") val address: String,
    @SerialName("Телефон") val phone: String,
    @SerialName("Email") val email: String,
    @SerialName("ФИОсудьи") val referee: String,
)
