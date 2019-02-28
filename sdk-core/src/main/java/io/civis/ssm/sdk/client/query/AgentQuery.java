package io.civis.ssm.sdk.client.query;

import io.civis.ssm.sdk.client.domain.Agent;
import io.civis.ssm.sdk.client.fabric.InvokeArgs;

public class AgentQuery extends Query {

    private static final String ROLE = "user";
    private final String agentName;

    public AgentQuery(String agentName) {
        this.agentName = agentName;
    }

    @Override
    public InvokeArgs queryArgs() {
        return new InvokeArgs(ROLE, agentName);
    }
}
