package io.civis.ssm.sdk.client.fabric;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

public class FabricUserFactory {

    public static FabricUserFactory factory(FabricClientFactory clientFactoty) {
        return new FabricUserFactory(clientFactoty);
    }

    private final FabricClientFactory fabricClientFactory;

    public FabricUserFactory(FabricClientFactory fabricClientFactory) {
        this.fabricClientFactory = fabricClientFactory;
    }

    //    public static FabricUser getUser(HFCAClient caClient, FabricUser registrar, String userId) throws Exception {
//        RegistrationRequest rr = new RegistrationRequest(userId, "bc-coop.bclan");
//        String enrollmentSecret = caClient.register(rr, registrar);
//        Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
//        return new FabricUser(userId, "bc-coop.bclan", enrollment,"BlockchainLANCoopMSP");
//    }
//
    public FabricUser getAdmin() throws Exception {
        HFCAClient caClient = fabricClientFactory.getHfCaClient();
        Enrollment adminEnrollment = caClient.enroll("df82a3b46bda4183fb691fa9b57a39b8", "121a59e3882a7e7344333772a79df5cc");
        return new FabricUser("admin", "bc-coop.bclan", adminEnrollment,"BlockchainLANCoopMSP");
    }

}
