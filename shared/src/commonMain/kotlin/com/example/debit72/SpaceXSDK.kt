package com.example.debit72

import com.example.debit72.database.Database
import com.example.debit72.database.DatabaseDriverFactory
import com.example.debit72.entity.IP
import com.example.debit72.repository.InfoRepository

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val repo = InfoRepository()

    @Throws(Exception::class)
    suspend fun getAllIp(forceReload: Boolean): List<IP> {
        val cachedLaunches = database.getAllIp()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            repo.getAllIp().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}