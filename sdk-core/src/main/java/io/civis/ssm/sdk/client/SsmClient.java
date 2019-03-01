package io.civis.ssm.sdk.client;

import io.civis.ssm.sdk.client.Utils.JsonUtils;
import io.civis.ssm.sdk.client.command.*;
import io.civis.ssm.sdk.client.domain.*;
import io.civis.ssm.sdk.client.fabric.FabricClient;
import io.civis.ssm.sdk.client.fabric.InvokeArgs;
import io.civis.ssm.sdk.client.query.*;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class SsmClient {

    private final Logger logger = LoggerFactory.getLogger(SsmClient.class);

    private static final String CHAIN_CODE = "ssm";
    private static final String CHANNEL = "sandbox";

    public static SsmClient fromConfigFile(String filename) throws IOException {
        FabricClient client = FabricClient.fromConfigFile(filename);
        return new SsmClient(client);
    }

    private final FabricClient fabricClient;

    public SsmClient(FabricClient fabricClient) {
        this.fabricClient = fabricClient;
    }

    public CompletableFuture<BlockEvent.TransactionEvent> registerUser(Signer signer, Agent agent) throws Exception {
        RegisterCommand cmd = new RegisterCommand(signer, agent);
        return invoke(cmd);
    }

    public CompletableFuture<BlockEvent.TransactionEvent> create(Signer signer, Ssm ssm) throws Exception {
        CreateCommand cmd = new CreateCommand(signer, ssm);
        return invoke(cmd);
    }

    public CompletableFuture<BlockEvent.TransactionEvent> start(Signer signer, Session session) throws Exception {
        StartCommand cmd = new StartCommand(signer, session);
        return invoke(cmd);
    }

    public CompletableFuture<BlockEvent.TransactionEvent> perform(Signer signer, String action, Context context) throws Exception {
        PerformCommand cmd = new PerformCommand(signer, action, context);
        return invoke(cmd);
    }

    public Agent getAdmin(String username) throws Exception {
        AdminQuery query = new AdminQuery(username);
        return query(query, Agent.class);
    }

    public Agent getAgent(String agentName) throws Exception {
        AgentQuery query = new AgentQuery(agentName);
        return query(query, Agent.class);
    }

    public Ssm getSsm(String name) throws Exception {
        SsmQuery query = new SsmQuery(name);
        return query(query, Ssm.class);
    }

    public SessionState getSession(String sessionId) throws Exception {
        SessionQuery query = new SessionQuery(sessionId);
        return query(query, SessionState.class);
    }

    private CompletableFuture<BlockEvent.TransactionEvent> invoke(Command cmd) throws Exception {
        InvokeArgs invokeArgs = cmd.invoke();
        logger.info("Send to the blockchain command[{}] with args:{}",cmd.getCommandName(), invokeArgs);
        return fabricClient.invoke(CHANNEL, CHAIN_CODE, invokeArgs);
    }

    private <T> T query(Query query, Class<T> clazz) throws Exception {
        InvokeArgs args = query.queryArgs();
        String value = fabricClient.query(CHANNEL, CHAIN_CODE, args);
        return JsonUtils.toObject(value, clazz);
    }

}
