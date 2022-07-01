package com.ellingtonb.webservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    value = ["com.ellingtonb"]
)
class WebserviceApplication

fun main(args: Array<String>) {
    runApplication<WebserviceApplication>(*args)
}
