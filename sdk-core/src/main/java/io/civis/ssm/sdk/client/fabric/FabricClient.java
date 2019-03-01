package io.civis.ssm.sdk.client.fabric;

import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class FabricClient {

    private static final Logger logger = LoggerFactory.getLogger(FabricClient.class);

    public static FabricClient fromConfigFile(String filename) throws IOException {
        FabricConfig fabricConfig = FabricConfig.loadFromFile(filename);
        FabricClientFactory clientFactoty = FabricClientFactory.factory(fabricConfig);
        FabricUserFactory userBuilder = FabricUserFactory.factory(clientFactoty);
        FabricChannelFactory channelFactory = FabricChannelFactory.factory(fabricConfig);
        return new FabricClient(clientFactoty, userBuilder, channelFactory);
    }

    private final FabricClientFactory clientFactoty;
    private final FabricUserFactory userBuilder;
    private final FabricChannelFactory channelFactory;


    public FabricClient(FabricClientFactory clientFactoty, FabricUserFactory userBuilder, FabricChannelFactory channelFactory) {
        this.clientFactoty = clientFactoty;
        this.userBuilder = userBuilder;
        this.channelFactory = channelFactory;
    }

    public CompletableFuture<BlockEvent.TransactionEvent> invoke(String channelName, String chainId, InvokeArgs invokeArgs) throws Exception {
        FabricUser admin = userBuilder.getAdmin();
        HFClient client = clientFactoty.getHfClient(admin);
        Channel channel = channelFactory.getChannel(client, channelName);
        ChaincodeID chanCodeId = ChaincodeID.newBuilder().setName(chainId).build();
        return invokeBlockChain(client, channel, chanCodeId, invokeArgs);
    }

    public String query(String channelName, String chainId, InvokeArgs invokeArgs) throws Exception {
        FabricUser admin = userBuilder.getAdmin();
        HFClient client = clientFactoty.getHfClient(admin);
        Channel channel = channelFactory.getChannel(client, channelName);
        ChaincodeID chanCodeId = ChaincodeID.newBuilder().setName(chainId).build();
        return queryBlockChain(client, channel, chanCodeId, invokeArgs);
    }

    private CompletableFuture<BlockEvent.TransactionEvent> invokeBlockChain(HFClient client, Channel channel, ChaincodeID chanCodeId, InvokeArgs invokeArgs) throws ProposalException, InvalidArgumentException {
        TransactionProposalRequest qpr = client.newTransactionProposalRequest();
        qpr.setChaincodeID(chanCodeId);
        qpr.setFcn(invokeArgs.getFunction());
        qpr.setArgs(invokeArgs.getValues());
        Collection<ProposalResponse> res = channel.sendTransactionProposal(qpr, channel.getPeers());

        return channel.sendTransaction(res);
    }

    private String queryBlockChain(HFClient client, Channel channel, ChaincodeID chanCodeId, InvokeArgs invokeArgs) throws ProposalException, InvalidArgumentException {
        QueryByChaincodeRequest qpr = client.newQueryProposalRequest();
        qpr.setChaincodeID(chanCodeId);
        qpr.setFcn(invokeArgs.getFunction());
        qpr.setArgs(invokeArgs.getValues());
        Collection<ProposalResponse> res = channel.queryByChaincode(qpr);
        return new String(res.iterator().next().getChaincodeActionResponsePayload());
    }

}
