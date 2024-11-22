package com.backend.commitoclock

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommitoclockApplication

fun main(args: Array<String>) {
    val dotenv = Dotenv.load()
    System.setProperty("MONGO_DB_HOST", dotenv["MONGO_DB_HOST"])
    System.setProperty("MONGO_DB_PORT", dotenv["MONGO_DB_PORT"])
    System.setProperty("MONGO_DB_DATABASE", dotenv["MONGO_DB_DATABASE"])
    System.setProperty("MONGO_DB_USERNAME", dotenv["MONGO_DB_USERNAME"])
    System.setProperty("MONGO_DB_PASSWORD", dotenv["MONGO_DB_PASSWORD"])
    runApplication<CommitoclockApplication>(*args)
}
