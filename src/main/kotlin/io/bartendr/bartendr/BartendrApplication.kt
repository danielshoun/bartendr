package io.bartendr.bartendr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BartendrApplication

fun main(args: Array<String>) {
    runApplication<BartendrApplication>(*args)
}
