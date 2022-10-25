package com.example.debit72.database

import com.example.debit72.entity.IP
import com.example.debit72.kmm.shared.cache.AppDatabase

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllLIP()
        }
    }

    internal fun getAllIp(): List<IP> {
        return dbQuery.selectAllIp(::mapLaunchSelecting).executeAsList()
    }

    private fun mapLaunchSelecting(
        idNumber: String,
        caseNumber: String,
        debtor: String,
        claimant: String,
        address: String,
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
            rosp = rosp,
            spi = spi,
            number = number,
            regNumberIP = regNumberIP,
            totalAmountDebt = totalAmountDebt,
            balanceOwed = balanceOwed,
        )
    }

    internal fun createLaunches(launches: List<IP>) {
        dbQuery.transaction {
            launches.forEach { launch ->
                val rocket = dbQuery.selectIpFromNumber(launch.number).executeAsOneOrNull()
                if (rocket == null) {
                    insertLaunch(launch)
                }

            }
        }
    }

    private fun insertLaunch(launch: IP) {
        dbQuery.insertIP(
            idNumber = launch.idNumber,
            caseNumber = launch.caseNumber,
            debtor = launch.debtor,
            claimant = launch.claimant,
            address = launch.address,
            rosp = launch.rosp,
            spi = launch.spi,
            number = launch.number,
            regNumberIP = launch.regNumberIP,
            totalAmountDebt = launch.totalAmountDebt,
            balanceOwed = launch.balanceOwed,
        )
    }
}