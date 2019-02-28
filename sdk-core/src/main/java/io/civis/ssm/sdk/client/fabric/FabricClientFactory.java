package io.civis.ssm.sdk.client.fabric;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.util.Properties;

public class FabricClientFactory {

    private static FabricConfig fabricConfig;

    public FabricClientFactory(FabricConfig fabricConfig) {
        this.fabricConfig = fabricConfig;
    }

    public static FabricClientFactory factory(FabricConfig fabricConfig) {
        return new FabricClientFactory(fabricConfig);
    }

    public HFClient getHfClient() throws Exception {
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(cryptoSuite);
        return client;
    }

    public HFCAClient getHfCaClient() throws Exception {
        Properties caClientProperties = fabricConfig.getCaTlsProperties();
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        HFCAClient caClient = HFCAClient.createNewInstance(fabricConfig.getCertificateAuthorityUrl(), caClientProperties);
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

    public HFClient getHfClient(FabricUser admin) throws Exception {
        HFClient client = getHfClient();
        client.setUserContext(admin);
        return client;
    }

}
