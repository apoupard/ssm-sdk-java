package io.civis.ssm.sdk.client.query;

import io.civis.ssm.sdk.client.fabric.InvokeArgs;

public class SsmQuery extends Query {

    private static final String FUNCTION = "ssm";
    private final String name;

    public SsmQuery(String name) {
        this.name = name;
    }

    @Override
    public InvokeArgs queryArgs() {
        return new InvokeArgs(FUNCTION, name);
    }
}
