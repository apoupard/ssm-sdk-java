package io.civis.ssm.sdk.client.query;

import io.civis.ssm.sdk.client.fabric.InvokeArgs;

public class AdminQuery extends Query {

    private static final String ROLE = "admin";
    private final String username;

    public AdminQuery(String username) {
        this.username = username;
    }

    @Override
    public InvokeArgs queryArgs() {
        return new InvokeArgs(ROLE, username);
    }
}
