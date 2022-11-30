package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AutoInBD(
    @SerialName("Владелец") val owner: String,
    @SerialName("МодельТС") val modelTs: String,
    @SerialName("ГосномерТС") val gosNumber: String,
    @SerialName("ТСАрестован") val arrested: Boolean,
    @SerialName("строкаПоиска") val searchLine: String,
    @SerialName("ипДолжника") val ipClaimant: String,
)
