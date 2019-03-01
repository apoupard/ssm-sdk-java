package io.civis.ssm.sdk.api.rest

import io.civis.ssm.sdk.api.rest.config.SsmConfig
import io.civis.ssm.sdk.client.fabric.FabricClient
import io.civis.ssm.sdk.client.fabric.InvokeArgs
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Function

@RestController
@RequestMapping("/ssm",  produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
class SsmController(val fabricClient: FabricClient, val ssmConfiguration: SsmConfig) {

    @PostMapping
    @ResponseBody
    fun command(@RequestBody args: InvokeArgs): Mono<String> {
        val future = fabricClient.invoke(ssmConfiguration.channel, ssmConfiguration.chainid, args);
        return Mono.fromFuture(future).map{ it -> it.transactionID };
    }

    @GetMapping
    @ResponseBody
    fun query(function: String, args: Array<String>): String? =
        fabricClient.query(ssmConfiguration.channel, ssmConfiguration.chainid, InvokeArgs(function, args.iterator()));

}