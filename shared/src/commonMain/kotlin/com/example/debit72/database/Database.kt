package com.example.debit72.database

import com.example.debit72.kmm.shared.cache.AppDatabase
import model.AutoInBD
import model.IP
import model.Spr

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearIP() {
        dbQuery.transaction {
            dbQuery.removeAllLIP()
        }
    }

    internal fun selectCount(): Long {
        return dbQuery.selectCount().executeAsOne()
    }

    internal fun selectIpFromNumber(number: Int): IP {
        return dbQuery.selectIpFromNumber(
            number,
            mapper = ::mapLaunchSelecting
        ).executeAsOne()
    }

    internal fun selectIpFromString(query: String): List<IP> {
        return dbQuery.selectIpFromString(
            query = query,
            mapper = ::mapLaunchSelecting
        ).executeAsList()
    }

    private fun mapLaunchSelecting(
        idNumber: String,
        caseNumber: String,
        debtor: String,
        claimant: String,
        address: String,
        addressForSearch: String,
        rosp: String,
        spi: String,
        number: Int,
        regNumberIP: String,
        totalAmountDebt: String,
        balanceOwed: String,
    ): IP {
        return IP(
            idNumber = idNumber,
            caseNumber = caseNumber,
            debtor = debtor,
            claimant = claimant,
            address = address,
            addressForSearch = addressForSearch,
            rosp = rosp,
            spi = spi,
            number = number,
            regNumberIP = regNumberIP,
            totalAmountDebt = totalAmountDebt,
            balanceOwed = balanceOwed,
        )
    }

    internal fun createIp(listIp: List<IP>) {
        dbQuery.transaction {
            listIp.forEach { launch ->
                val rocket = dbQuery.selectIpFromNumber(launch.number).executeAsOneOrNull()
                if (rocket == null) {
                    insertIp(launch)
                }

            }
        }
    }


    private fun insertIp(ip: IP) {
        dbQuery.insertIP(
            idNumber = ip.idNumber,
            caseNumber = ip.caseNumber,
            debtor = ip.debtor,
            claimant = ip.claimant,
            address = ip.address,
            addressForSearch = ip.addressForSearch,
            rosp = ip.rosp,
            spi = ip.spi,
            number = ip.number,
            regNumberIP = ip.regNumberIP,
            totalAmountDebt = ip.totalAmountDebt,
            balanceOwed = ip.balanceOwed,
        )
    }

    /**
     * БЛОК Приказной работы
     * */

    internal fun clearSpr() {
        dbQuery.transaction {
            dbQuery.removeAllSpr()
        }
    }

    internal fun createSpr(listSpr: List<Spr>) {
        dbQuery.transaction {
            listSpr.forEach { spr ->
                dbQuery.insertSpr(
                    naKogo = spr.naKogo,
                    claimant = spr.claimant,
                    submissionDate = spr.submissionDate,
                    court = spr.court,
                    currentCourt = spr.currentCourt,
                    address = spr.address,
                    addressForSearch = spr.addressForSearch,
                    number = spr.number
                )
            }
        }
    }

    internal fun selectSprFromString(query: String): List<Spr> {
        return dbQuery.selectSprFromString(
            query = query,
            mapper = ::mapSprSelecting
        ).executeAsList()
    }

    private fun mapSprSelecting(
        naKogo: String,
        claimant: String,
        submissionDate: String,
        court: String,
        currentCourt: String,
        address: String,
        addressForSearch: String,
        number: String,
    ): Spr {
        return Spr(
            naKogo = naKogo,
            claimant = claimant,
            submissionDate = submissionDate,
            court = court,
            currentCourt = currentCourt,
            address = address,
            number = number,
            addressForSearch = addressForSearch
        )
    }

    internal fun selectCountSpr(): Long {
        return dbQuery.selectCountSpr().executeAsOne()
    }

    /**
     * БЛОК автомобили
     * */

    private fun insertAuto(auto: AutoInBD) {
        dbQuery.insertAutoInDB(
            owner = auto.owner,
            arrested = auto.arrested,
            gosNumber = auto.gosNumber,
            ipClaimant = auto.ipClaimant,
            modelTs = auto.modelTs,
            searchLine = auto.searchLine
        )
    }

    internal fun clearAuto() {
        dbQuery.transaction {
            dbQuery.removeAllAutoInDB()
        }
    }

    internal fun createAuto(listAuto: List<AutoInBD>) {
        dbQuery.transaction {
            listAuto.forEach { auto ->
                insertAuto(auto)
            }
        }
    }

    internal fun selectAutoFromString(query: String): List<AutoInBD> {
        return dbQuery.selectAutoFromString(
            query = query,
            mapper = ::mapAutoDataBaseToAutoBD
        ).executeAsList()
    }

    private fun mapAutoDataBaseToAutoBD(
        owner: String,
        modelTs: String,
        gosNumber: String,
        arrested: Boolean,
        searchLine: String,
        ipClaimant: String,
    ): AutoInBD {
        return AutoInBD(
            owner = owner,
            arrested = arrested,
            gosNumber = gosNumber,
            ipClaimant = ipClaimant,
            modelTs = modelTs,
            searchLine = searchLine,
        )
    }

    internal fun selectCountAuto(): Long {
        return dbQuery.selectCountAutoInDb().executeAsOne()
    }

}