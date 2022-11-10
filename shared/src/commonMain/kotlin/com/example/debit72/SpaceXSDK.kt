package com.example.debit72

import com.example.debit72.database.Database
import com.example.debit72.database.DatabaseDriverFactory
import com.example.debit72.repository.InfoRepository
import com.example.debit72.repository.SprRepository
import model.IP
import model.Spr

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val repoInfo = InfoRepository()
    private val repoSpr = SprRepository()

    @Throws(Exception::class)
    suspend fun updateIP(forceReload: Boolean) {
        if (forceReload) {
            repoInfo.getAllIp().also {
                database.clearIP()
                database.createIp(it)
            }
        }
    }

    @Throws(Exception::class)
    suspend fun selectCount(): Long {
        return database.selectCount()
    }

    @Throws(Exception::class)
    fun selectIpFromString(query: String): List<IP> {
        return database.selectIpFromString(query)
    }

    /**
     * БЛОК Приказной работы
     * */
    @Throws(Exception::class)
    suspend fun updateSpr(forceReload: Boolean) {
        if (forceReload) {
            repoSpr.getAllSpr().also {
                database.clearSpr()
                database.createSpr(it)
            }
        }
    }

    @Throws(Exception::class)
    suspend fun selectSprFromString(query: String): List<Spr> {
        return database.selectSprFromString(query)
    }

    @Throws(Exception::class)
    suspend fun selectCountSpr(): Long {
        return database.selectCountSpr()
    }


}