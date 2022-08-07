package com.example.debit72

class Greeting {
    fun greeting(): String {
        return "Guess what it is! > ${Platform().platform.reversed()}!"
    }
}