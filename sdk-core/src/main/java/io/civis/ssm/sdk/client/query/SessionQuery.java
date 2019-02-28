package io.civis.ssm.sdk.client.query;

import io.civis.ssm.sdk.client.fabric.InvokeArgs;

public class SessionQuery extends Query {

    private static final String FUNCTION = "session";
    private final String sessionId;

    public SessionQuery(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public InvokeArgs queryArgs() {
        return new InvokeArgs(FUNCTION, sessionId);
    }
}
