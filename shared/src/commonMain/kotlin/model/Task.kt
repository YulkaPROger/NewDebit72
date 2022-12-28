package model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Task(
    @SerialName("Исполнитель") val executor: String,
    @SerialName("Описание") val description: String,
    @SerialName("Задача") val name: String,
)