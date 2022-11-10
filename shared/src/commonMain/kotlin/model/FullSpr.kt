package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullSpr(
    @SerialName("Номер") val number: String,
    @SerialName("Дата") val date: String,
    @SerialName("АдресПроживания") val addressFact: String,
    @SerialName("Архив") val archive: Boolean,
    @SerialName("Взыскатель") val claimant: String,
    @SerialName("ГотовоКПодачеВСуд") val readyForCourt: Boolean,
    @SerialName("ДатаНачала") val dateStart: String,
    @SerialName("ДатаПодачиЗаявления") val dateGetToCourt: String,
    @SerialName("Должник") val debtor: String,
    @SerialName("Дольщик") val shareDebtor: Boolean,
    @SerialName("ДоляДольщика") val pieShareDebtor: String,
    @SerialName("Исключение") val exception: Boolean,
    @SerialName("КадастровыйНомер") val regNumber: String,
    @SerialName("ЛицевойСчет") val ls: String,
    @SerialName("НаКогоПодаемВСуд") val naKogoPutToCourt: String,
    @SerialName("ОтправленоВСуд") val getToCourt: Boolean,
    @SerialName("ПериодДолгаПо") val periodPo: String,
    @SerialName("ПериодДолгаС") val periodS: String,
    @SerialName("Суд") val court: String,
    @SerialName("СудРассматривающийЗаявление") val currentCourt: String,
    @SerialName("СуммаДолга") val debt: String,
    @SerialName("СуммаДолгаДольщика") val debtShareDebtor: String,
    @SerialName("СуммаЖКУИПени") val sumOfZHKU: String,
    @SerialName("СуммаПени") val penalties: String,
    @SerialName("СуммаПениДольщика") val penaltiesShareDebtor: String,
    @SerialName("СуммаПошлины") val duty: String,
    @SerialName("СуммаПошлиныДольщика") val dutyShareDebtor: String,
    @SerialName("СудКод") val courtCode: String,
    @SerialName("СудРассмЗаявлениеКод") val currentCourtCode: String,
    @SerialName("Адрес") val address: String,
)
