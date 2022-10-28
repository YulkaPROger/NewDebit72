package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullIP(
    @SerialName("НомерСтрокиИзТабExcel") val numberExcel: String,
    @SerialName("ОбщаяСуммаДолга") val totalAmountDebt: String,
    @SerialName("ОстатокДолга") val balanceOwed: String,
    @SerialName("ОстатокФССП") val balanceFSSP: String,
    @SerialName("Аннулировано") val canceled: Boolean,
    @SerialName("ПричинаАннуляции") val reasonForCancellation: String,
    @SerialName("ПериодС") val periodFrom: String,
    @SerialName("ПериодДо") val periodTo: String,
    @SerialName("ДатаПодачи") val dateOfApplication: String,
    @SerialName("ДатаВозбуждения") val dateExcitation: String,
    @SerialName("ДатаЗавершенияИп") val dateCancelledIP: String,
    @SerialName("РегНомерИП") val registryNumberIP: String,
    @SerialName("СтатусПроизводства") val productionStatus: String,
    @SerialName("ФССП") val fssp: String,
    @SerialName("СПИ") val spi: String,
    @SerialName("ТипИД") val typeID: String,
    @SerialName("Суд") val court: String,
    @SerialName("ДатаИД") val dateID: String,
    @SerialName("НомерИД") val numberID: String,
    @SerialName("НомерДела") val caseNumber: String,
    @SerialName("Адресс") val address: String,
    @SerialName("Должник") val debtor: String,
    @SerialName("АдрессДолжника") val debtorAddress: String,
    @SerialName("ДолжникДР") val debtorBirthday: String,
    @SerialName("Пенсионер") val pensioner: Boolean,
    @SerialName("МестоРождения") val placeOfBirth: String,
    @SerialName("Умер") val died: Boolean,
    @SerialName("ЛицевойСчет") val ls: String,
    @SerialName("ДолжникАдресФактический") val debtorAddressFact: String,
    @SerialName("Солидарно") val solidarity: Boolean,
    @SerialName("ЖКУ") val ZHKU: String,
    @SerialName("Пени") val penalties: String,
    @SerialName("Пошлина") val duty: String,
    @SerialName("ЮрУслуги") val yurServices: String,
    @SerialName("Банк") val bank: String,
    @SerialName("ДатаПодачиБанк") val dateSubmissionBank: String,
    @SerialName("ДатаОтзываБанк") val dateRevokedBank: String,
    @SerialName("СтатусБанк") val bankStatus: String,
    @SerialName("ПризнакЛС") val signLS: Boolean,
    @SerialName("СписокСолидарщиков") val listOfSolidarity: String,
//    @SerialName("qr") val qr: String,
    @SerialName("Авто") val auto: List<Auto> = emptyList(),
    @SerialName("Имущество") val property: List<Property> = emptyList(),
    @SerialName("ИмуществоЕГРЮЛ") val propertyYur: List<PropertyYur> = emptyList(),
    @SerialName("Работодатели") val employer: List<Employer> = emptyList(),
    @SerialName("RS") val rs: List<RS> = emptyList(),
)

@Serializable
data class Auto(
    @SerialName("Авто") val auto: String,
    @SerialName("ГосномерТС") val gosNumber: String,
    @SerialName("МодельТС") val modelTS: String,
    @SerialName("Арестовано") val arrested: Boolean,
    @SerialName("Реализован") val realized: Boolean,
    @SerialName("ДатаАрестаТС") val dateOfArrestedTS: String,
    @SerialName("СуммаПоОценкеТС") val priceTS: String,
    @SerialName("МестоХранения") val storage: String,
)

@Serializable
data class Property(
    @SerialName("ОбъектыНедвижимостиДолжника") val objectProperty: String,
    @SerialName("Арестовано") val arrested: Boolean,
    @SerialName("ДатаОписиАреста") val dateArrested: String,
    @SerialName("СуммаПоОценке") val price: String,
)

@Serializable
data class PropertyYur(
    @SerialName("ИмуществоЕГРЮЛ") val propertyYur: String,
    @SerialName("АдресЮЛ") val address: String,
    @SerialName("ИНН") val inn: String,
    @SerialName("ОГРН") val ogrn: String,
    @SerialName("СтоимостьДоли") val priceCost: String,
    @SerialName("РазмерДоли") val sizeCost: String,
)

@Serializable
data class Employer(
    @SerialName("Работодатель") val employer: String,
    @SerialName("АдресРаботодателя") val employerAddress: String,
    @SerialName("ДатаАктуальностиСведений") val actualDate: String,
    @SerialName("ОбращениеВзыскания") val appealRecovery: String,
    @SerialName("ДатаОбращенияВзыскания") val dateAppealRecovery: String,
    @SerialName("ШПИ") val shpi: String,
)

@Serializable
data class RS(
    @SerialName("НомерСчета") val number: String,
    @SerialName("Банк") val bank: String,
    @SerialName("ВидСчета") val type: String,
)

