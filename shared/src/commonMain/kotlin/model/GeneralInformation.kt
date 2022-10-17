package model

import kotlinx.serialization.Serializable

@Serializable
data class GeneralInformation(
    val count: String,
    val balanceOwed: String,
    val totalDebt: String,
    val amountOfDuty: String,
    val amountOfLegalServices: String,
    val amountPenalty: String,
    val amountOfCommunalServices: String
)