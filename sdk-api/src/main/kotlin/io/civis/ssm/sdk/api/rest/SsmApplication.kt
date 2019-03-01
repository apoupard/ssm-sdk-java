package io.civis.ssm.sdk.api.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [SsmApplication::class] )
class SsmApplication

fun main(args: Array<String>) {
	runApplication<SsmApplication>(*args)
}