package io.civis.ssm.sdk.client.command;

import io.civis.ssm.sdk.client.domain.Context;
import io.civis.ssm.sdk.client.domain.Signer;
import io.civis.ssm.sdk.client.fabric.InvokeArgs;
import io.civis.ssm.sdk.client.Utils.KeyPairReader;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

public class PerformCommandTest {


    @Test
    public void test_execute() throws Exception {

        KeyPair samPair = KeyPairReader.loadKeyPair("sam");

        Signer signer = new Signer("sam", samPair);

//        "{\"session\":\"deal20181201\",\"public\":\"100 dollars 1978 Camaro\",\"iteration\":0}"
        Context context = new Context("deal20181201", "100 dollars 1978 Camaro", 0);

        InvokeArgs invokeArgs = new PerformCommand(signer, "Sell", context).invoke();
        invokeArgs.getValues().forEach(System.out::println);

        assertThat(invokeArgs.getFunction()).isEqualTo("perform");
        assertThat(invokeArgs.getValues())
                .isNotEmpty()
                .containsExactly(
                        "Sell",
                        "{\"session\":\"deal20181201\",\"public\":\"100 dollars 1978 Camaro\",\"iteration\":0}",
                        "sam",
                        "ANOVTS1mqW2M+u7IoR/A/S3lY2xnj7yS8fJg0k0XE3DeY+i23LzJL1ABrm/CxHbndqVtvmsDQ0pLJ/XGmdAxhpTSAj+oIi+bnQAxW5fAqtLt9KHOElnG7KWzO8xitHh7NaIDgMbMjxJ5tj8xRFB2OD69P6aqtv9sj6TqkIhWNCMYPzDl+/Rck3Su7/51heDeTkDjtPxnkyOYTnSTJF7eFaMTyauqAqtjQwznL9xhKIlxMcmLxmboEDNQd8tv3mT/8ALGhmo1YUWtMkgJ00li3NDhjq1+gVNjAcUpBhwN/N8lUmN6MElc9qwliHVOsWkwHAYvZ7r6Zdvf6typbFqkeA=="
                );

    }

}