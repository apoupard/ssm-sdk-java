package io.civis.ssm.sdk.client.fabric;

import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.util.Properties;

public class FabricChannelFactory {

    private final FabricConfig fabricConfig;

    public FabricChannelFactory(FabricConfig fabricConfig) {
        this.fabricConfig = fabricConfig;
    }

    public static FabricChannelFactory factory(FabricConfig fabricConfig) {
        return new FabricChannelFactory(fabricConfig);
    }

    public Channel getChannel(HFClient client, String channelName) throws InvalidArgumentException, TransactionException, IOException {
        Properties prop = fabricConfig.getPeerTlsProperties();
        Peer peer = client.newPeer(fabricConfig.getPeerName(), fabricConfig.getPeerUrl(), prop);

//        EventHub eventHub = client.newEventHub(fabricConfig.getEventHubName(), fabricConfig.getEventHubUrl());

        Properties ordererProp = fabricConfig.getOrdererTlsProperties();
        Orderer orderer = client.newOrderer(fabricConfig.getOrdererName(), fabricConfig.getOrdererUrl(), ordererProp);

        return client.newChannel(channelName)
                .addPeer(peer)
//                .addEventHub(eventHub)
                .addOrderer(orderer)
                .initialize();
    }
}
