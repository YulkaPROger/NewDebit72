package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClaimantsOnTheRosp(
    @SerialName("РОСП") val rosp: String,
    @SerialName("Взыскатели") val claimant: List<ClaimantOnRosp>,

    ) {
    @Serializable
    data class ClaimantOnRosp(
        @SerialName("КоличествоДел") val countCase: Int,
        @SerialName("Взыскатель") val claimant: String,
        @SerialName("ИНН") val inn: String,

    )
}