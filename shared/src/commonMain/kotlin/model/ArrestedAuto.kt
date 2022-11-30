package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ArrestedAuto(
    @SerialName("МодельТС") val modelTs: String,
    @SerialName("ГосномерТС") val gosNumber: String,
    @SerialName("ДатаАрестаТС") val dateArrestedTs: String,
    @SerialName("МестоХранения") val repository: String,
    @SerialName("СуммаПоОценкеТС") val price: String,
    @SerialName("Владелец") val owner: String,
    @SerialName("строкаИП") val ipDebtor: String,
    @SerialName("строкаПоиска") val searchLine: String,
)

