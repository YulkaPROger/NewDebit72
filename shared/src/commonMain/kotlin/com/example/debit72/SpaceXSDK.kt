package com.example.debit72

import com.example.debit72.database.Database
import com.example.debit72.database.DatabaseDriverFactory
import com.example.debit72.repository.*
import model.*

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val repoInfo = InfoRepository()
    private val repoSpr = SprRepository()
    private val repoAuto = AutoRepository()
    private val repoArrestedAuto = ArrestedAutoRepository()
    private val repoArrestedProperty = ArrestedPropertyRepository()

    @Throws(Exception::class)
    suspend fun updateIP() {
        repoInfo.getAllIp().also {
            database.clearIP()
            database.createIp(it)
        }
    }

    @Throws(Exception::class)
    fun selectCount(): Long {
        return database.selectCount()
    }

    @Throws(Exception::class)
    fun selectIpFromString(query: String): List<IP> {
        return database.selectIpFromString(query)
    }

    fun selectIpFromNumber(number: Int): IP {
        return database.selectIpFromNumber(number)
    }

    /**
     * БЛОК Приказной работы
     * */
    @Throws(Exception::class)
    suspend fun updateSpr() {
        repoSpr.getAllSpr().also {
            database.clearSpr()
            database.createSpr(it)
        }
    }

    @Throws(Exception::class)
    fun selectSprFromString(query: String): List<Spr> {
        return database.selectSprFromString(query)
    }

    @Throws(Exception::class)
    fun selectCountSpr(): Long {
        return database.selectCountSpr()
    }

    /**
     * БЛОК автомобили
     * */
    @Throws(Exception::class)
    suspend fun updateAuto() {
        repoAuto.getAutoList().also {
            database.clearAuto()
            database.createAuto(it)
        }
    }

    @Throws(Exception::class)
    fun selectAutoFromString(query: String): List<AutoInBD> {
        return database.selectAutoFromString(query)
    }

    @Throws(Exception::class)
    fun selectCountAuto(): Long {
        return database.selectCountAuto()
    }

    /**
     * БЛОК арестованных автомобилей
     * */
    @Throws(Exception::class)
    suspend fun updateArrestedAuto() {
        repoArrestedAuto.getArrestedAutoList().also {
            database.clearArrestedAuto()
            database.createArrestedAuto(it)
        }
    }

    @Throws(Exception::class)
    fun selectArrestedAutoFromString(query: String): List<ArrestedAuto> {
        return database.selectArrestedAutoFromString(query)
    }

    @Throws(Exception::class)
    fun selectCountArrestedAuto(): Long {
        return database.selectCountArrestedAuto()
    }

    @Throws(Exception::class)
    fun selectAllArrestedAuto(): List<ArrestedAuto> {
        return database.selectAllArrestedAuto()
    }

    /**
     * БЛОК арестованнного имущества
     * */
    @Throws(Exception::class)
    suspend fun updateArrestedProperty() {
        repoArrestedProperty.getArrestedPropertyList().also {
            database.clearArrestedAProperty()
            database.createArrestedProperty(it)
        }
    }

    @Throws(Exception::class)
    fun selectArrestedPropertyFromString(query: String): List<ArrestedProperty> {
        return database.selectArrestedPropertyFromString(query)
    }

    @Throws(Exception::class)
    fun selectCountArrestedProperty(): Long {
        return database.selectCountArrestedProperty()
    }

    @Throws(Exception::class)
    fun selectAllArrestedProperty(): List<ArrestedProperty> {
        return database.selectAllArrestedProperty()
    }

}