# Java Sdk for blockchain-ssm

[Blockchain-ssm](https://github.com/civis-blockchain/blockchain-ssm) is a signing state machines chaincode developped for Hyperleger Fabric.  
This sdk has been tested with [bclan configuration](https://github.com/civis-blockchain/blockchain-coop/tree/master/bclan) fabric network configuration

## Configuration exemple

### fabric.properties
```
certificate_authority.url=https://ca.bc-coop.bclan:7054
certificate_authority.tls.allowAllHostNames=true
certificate_authority.tls.file=ca.bc-coop.bclan.tls.crt

peer.name=peer0.bc-coop.bclan
peer.url=grpcs://peer0.bc-coop.bclan:7051
peer.tls.allowAllHostNames=true
peer.tls.pemFile=peer0.tls.crt

orderer_name=orderer.bclan
orderer_url=grpcs://orderer.bclan:7050
orderer_url.tls.allowAllHostNames=true
orderer_url.tls.pemFile=orderer.tls.crt
```

### Certificates
ca.bc-coop.bclan.tls.crt
```
openssl s_client -showcerts -connect ca.bc-coop.bclan:7054 2>&1 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > sdk-core/src/test/resources/ca.bc-coop.bclan.tls.crt
```

peer0.tls.crt
```
openssl s_client -showcerts -connect peer0.bc-coop.bclan:7051 2>&1 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > sdk-core/src/test/resources/peer0.tls.crt
```

orderer.tls.crt
```
openssl s_client -showcerts -connect orderer.bclan:7050 2>&1 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > sdk-core/src/test/resources/orderer.tls.crt
```

For security reason in java, urls must match the certificate value.Add to /etc/hosts
```bash
echo '127.0.0.1  ca.bc-coop.bclan' >> .env
echo '127.0.0.1	peer0.bc-coop.bclan' >> .env
echo '127.0.0.1	orderer.bclan' >> .env
```

# Usage
An example can be find in unit test
``` 
sdk-core/src/test/java/io/civis/ssm/sdk/client/SsmClientItTest.java
```