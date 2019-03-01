package io.civis.ssm.sdk.api.rest.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SsmConfig {

    @Value("\${fabric.channel}")
   lateinit var channel: String

    @Value("\${fabric.chainid}")
    lateinit var chainid: String


}