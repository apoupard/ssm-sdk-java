package io.civis.ssm.sdk.client;


import com.google.common.collect.ImmutableMap;
import io.civis.ssm.sdk.client.domain.*;
import org.assertj.core.util.Lists;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SsmClientItTest {

    private static Signer signerAdam;
    private static Signer signerSam;
    private static SsmClient client;
    private static String ssmName;
    private static String sessionName;
    private static Session session;

    @BeforeAll
    public static void init() throws Exception {
        signerAdam = Signer.loadFromFile("adam");
        signerSam = Signer.loadFromFile("sam");
        client = SsmClient.fromConfigFile("fabric.properties");
        ssmName = "CarDealership-" + UUID.randomUUID().toString();
        Map<String, String> roles = ImmutableMap.of("bob", "Buyer", "sam", "Seller");
        sessionName = "deal20181201";
        session = new Session(ssmName, sessionName, "Used car for 100 dollars.", roles);
    }

    @Test
    @Order(10)
    public void getAdminUser() throws Exception {
        Agent agent = Agent.loadFromFile("adam");
        Agent agentRet = client.getAdmin("adam");

        assertThat(agentRet).isEqualTo(agent);
    }

    @Test
    @Order(20)
    public void registerBob() throws Exception {

        Agent agent = Agent.loadFromFile("bob");
        CompletableFuture<BlockEvent.TransactionEvent> transactionEvent = client.registerUser(signerAdam, agent);
        BlockEvent.TransactionEvent trans = transactionEvent.get();

        assertThat(trans.isValid()).isTrue();

    }

    @Test
    @Order(30)
    public void getAgentBob() throws Exception {
        Agent agent = Agent.loadFromFile("bob");
        Agent agentRet = client.getAgent("bob");
        assertThat(agentRet).isEqualTo(agent);
    }

    @Test
    @Order(40)
    public void registerSam() throws Exception {
        Agent agent = Agent.loadFromFile("sam");
        CompletableFuture<BlockEvent.TransactionEvent> transactionEvent = client.registerUser(signerAdam, agent);
        BlockEvent.TransactionEvent trans = transactionEvent.get();
        assertThat(trans.isValid()).isTrue();
    }

    @Test
    @Order(50)
    public void getAgentSam() throws Exception {
        Agent agent = Agent.loadFromFile("sam");
        Agent agentRet = client.getAgent("sam");
        assertThat(agentRet).isEqualTo(agent);
    }


    @Test
    @Order(60)
    public void createSsm() throws Exception {

        Ssm.Transition sell = new Ssm.Transition(0, 1, "Seller", "Sell");
        Ssm.Transition buy = new Ssm.Transition(1, 2, "Buyer", "Buy");
        Ssm ssm = new Ssm(ssmName, Lists.newArrayList(sell, buy));

        CompletableFuture<BlockEvent.TransactionEvent> transactionEvent = client.create(signerAdam, ssm);
        BlockEvent.TransactionEvent trans = transactionEvent.get();

        assertThat(trans.isValid()).isTrue();
    }

    @Test
    @Order(70)
    public void getSsm() throws Exception {

        Ssm ssmReq = client.getSsm(ssmName);

        assertThat(ssmReq).isNotNull();
        assertThat(ssmReq.getName()).isEqualTo(ssmName);
    }

    @Test
    @Order(80)
    public void start() throws Exception {
        Map<String, String> roles = ImmutableMap.of("bob", "Buyer", "sam", "Seller");
        Session session = new Session(ssmName, sessionName, "Used car for 100 dollars.", roles);


        CompletableFuture<BlockEvent.TransactionEvent> transactionEvent = client.start(signerAdam, session);
        BlockEvent.TransactionEvent trans = transactionEvent.get();

        assertThat(trans.isValid()).isTrue();
    }

    @Test
    @Order(90)
    public void getSession() throws Exception {


        SessionState sesReq = client.getSession(sessionName);

        assertThat(sesReq.getCurrent()).isEqualTo(0);
        assertThat(sesReq.getIteration()).isEqualTo(0);
        assertThat(sesReq.getOrigin()).isNull();

        assertThat(sesReq.getSsm()).isEqualTo(ssmName);
        assertThat(sesReq.getRoles()).isEqualTo(session.getRoles());
        assertThat(sesReq.getSession()).isEqualTo(session.getSession());
        assertThat(sesReq.getPublicMessage()).isEqualTo(session.getPublicMessage());

    }

    @Test
    @Order(100)
    public void performSell() throws Exception {
        Context context = new Context(sessionName, "100 dollars 1978 Camaro", 0);
        CompletableFuture<BlockEvent.TransactionEvent> transactionEvent = client.perform(signerSam, "Sell", context);
        BlockEvent.TransactionEvent trans = transactionEvent.get();
        assertThat(trans.isValid()).isTrue();
    }

    @Test
    @Order(110)
    public void getSessionAfterSell() throws Exception {
        Ssm.Transition sell = new Ssm.Transition(0, 1, "Seller", "Sell");
        SessionState sesReq = client.getSession(sessionName);

        SessionState stateExcpected = new SessionState(ssmName, sessionName, "100 dollars 1978 Camaro", session.getRoles(), sell, 1, 1);
        assertThat(sesReq).isEqualTo(stateExcpected);

    }

    @Test
    @Order(120)
    public void performBuy() throws Exception {
        Signer signerBob = Signer.loadFromFile("bob");
        Context context = new Context(sessionName, "Deal !", 1);
        CompletableFuture<BlockEvent.TransactionEvent> transactionEvent = client.perform(signerBob, "Buy", context);
        BlockEvent.TransactionEvent trans = transactionEvent.get();
        assertThat(trans.isValid()).isTrue();
    }

    @Test
    @Order(130)
    public void getSessionAfterBuy() throws Exception {
        Ssm.Transition buy = new Ssm.Transition(1, 2, "Buyer", "Buy");
        SessionState sesReq = client.getSession(sessionName);

        SessionState stateExcpected = new SessionState(ssmName, sessionName, "Deal !", session.getRoles(), buy, 2, 2);
        assertThat(sesReq).isEqualTo(stateExcpected);
    }

}
