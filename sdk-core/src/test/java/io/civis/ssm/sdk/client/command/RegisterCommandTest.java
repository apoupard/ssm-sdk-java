package io.civis.ssm.sdk.client.command;


import io.civis.ssm.sdk.client.domain.Signer;
import io.civis.ssm.sdk.client.domain.Agent;
import io.civis.ssm.sdk.client.fabric.InvokeArgs;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RegisterCommandTest {

    @Test
    public void test_execute() throws Exception {
        Signer signer = Signer.loadFromFile("adam");
        Agent agent = Agent.loadFromFile("bob");

        InvokeArgs invokeArgs = new RegisterCommand(signer, agent).invoke();
        invokeArgs.getValues().forEach(System.out::println);

        assertThat(invokeArgs.getFunction()).isEqualTo("register");
        assertThat(invokeArgs.getValues())
                .isNotEmpty()
                .containsExactly(
                        "{\"name\":\"bob\",\"pub\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq5UG+Oy0R9nob5EtCxdL9T2GIGsHMCnEffULsC/54wpVydW7Dj6AHIA7IsfidXGjqdd8gqRTcfCV3ctfvAjDavwv/6pvADlXW8qsK3OSK6kx5qyIXm6AW93RIG6ZYPuIKQn0n2AjWWV630RqYdDk1mS3W43L1aKWlL3X2UI3ARwc3+jrWsailci4ijXlNSptYx+To2bUPSMP179TDM3icb1M7QQhNlEn2hnceVuNNntSAA5QhpYyeHdpu4EV1vXwuZK93VW5sJ8zG2pW1afTJ9x58bFbLYmyUFRQAcMinl50ilHIrVjJsUCGy2xRRYKCdqMOebMrGK6QSfUXLnd17wIDAQAB\"}",
                        "adam",
                        "ht7TRpp6Y0S15BA3BjGbmfMwMM3RDQ0wTDpM3saT9RjCtP0YkTMrOQ3RWUbQONGdsFgfTPVuyyjxFDNCpa4gAhML0+Yx9FQU/LFYoL6K+LygUpR8LZiyZhYpjddbeBQn0UsVIbZuV8X/Puu7xtDD9oofRH+BXac1mNov2uaCoBBaBEXOh5ow6WmmqGTbMgMorZFaheU327Lw82O8cmjpJjiCDF4gKUMzl7fzwAr8GamjgMu14AsOJzsDkTQqG7isfnrWeZhi3L0ToAp6mi9QcDZJ2n3tBz1R7RGt5EUU8D04qkBLZEuuGLCldY1zRqE385Oh+vTnlbZ33tp6+MQ/cQ=="
                );

    }

}