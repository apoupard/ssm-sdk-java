package io.civis.ssm.sdk.api.rest.config

import io.civis.ssm.sdk.client.fabric.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SsmSdkConfig {

    @Bean
    fun getFabricConfig(): FabricConfig = FabricConfig.loadFromFile("fabric.properties")

    @Bean
    fun getFabricClientFactory(fabricConfig: FabricConfig): FabricClientFactory = FabricClientFactory.factory(fabricConfig)

    @Bean
    fun getFabricUserFactory(fabricClientFactory: FabricClientFactory): FabricUserFactory = FabricUserFactory.factory(fabricClientFactory)

    @Bean
    fun getFabricChannelFactory(fabricConfig: FabricConfig): FabricChannelFactory = FabricChannelFactory.factory(fabricConfig)

    @Bean
    fun getFabricClient(fabricClientFactory: FabricClientFactory,
                        fabricUserFactory: FabricUserFactory,
                        fabricChannelFactory: FabricChannelFactory): FabricClient = FabricClient(fabricClientFactory, fabricUserFactory, fabricChannelFactory)


}