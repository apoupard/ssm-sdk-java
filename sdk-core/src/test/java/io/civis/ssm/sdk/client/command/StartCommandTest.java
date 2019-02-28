package io.civis.ssm.sdk.client.command;

import com.google.common.collect.ImmutableMap;
import io.civis.ssm.sdk.client.Utils.SignUtils;
import io.civis.ssm.sdk.client.domain.Session;
import io.civis.ssm.sdk.client.domain.Signer;
import io.civis.ssm.sdk.client.fabric.InvokeArgs;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StartCommandTest {

    @Test
    public void test_execute() throws Exception {

        //    "ssm":"Car dealership",
        //    "session":"deal20181201",
        //    "public":"Used car for 100 dollars.",
        //    "roles":{
        //      "bob":"Buyer",
        //       "sam":"Seller"
        //    }
        Map<String, String> roles = ImmutableMap.of("bob", "Buyer","sam","Seller");
        Signer signer = Signer.loadFromFile("adam");
        Session session = new Session("Car dealership", "deal20181201", "Used car for 100 dollars.", roles);

        InvokeArgs invokeArgs = new StartCommand(signer, session).invoke();
        invokeArgs.getValues().forEach(System.out::println);

        String expectedJson = "{\"ssm\":\"Car dealership\",\"session\":\"deal20181201\",\"public\":\"Used car for 100 dollars.\",\"roles\":{\"bob\":\"Buyer\",\"sam\":\"Seller\"}}";
        assertThat(invokeArgs.getFunction()).isEqualTo("start");
        assertThat(invokeArgs.getValues())
                .isNotEmpty()
                .containsExactly(
                        expectedJson,
                        "adam",
                        SignUtils.rsaSignAsB64(expectedJson, signer.getPair().getPrivate())
                );

    }

}