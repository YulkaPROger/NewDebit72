package com.example.debit72.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListIP(
    val listIP: List<IP>
)

@Serializable
data class IP(
    @SerialName("НомерИД")
    val idNumber: String,

    @SerialName("НомерДела")
    val caseNumber: String,

    @SerialName("Должник")
    val debtor: String,

    @SerialName("Взыскатель")
    val claimant: String,

    @SerialName("Адресс")
    val address: String,

    @SerialName("росп")
    val rosp: String,

    @SerialName("СПИ")
    val spi: String,

    @SerialName("Номер")
    val number: Int,

    @SerialName("РегНомерИП")
    val regNumberIP: String,

    @SerialName("ОбщаяСуммаДолга")
    val totalAmountDebt: String,

    @SerialName("ОстатокДолга")
    val balanceOwed: String,
)

