#SDK API

## Configuration
sdk-core configuration and certificate must put in: `sdk-api/src/main/resources/`
  
Update sdk-api/src/main/resources/application.yml
```
fabric:
  channel: sandbox
  chainid: ssm
```

## Start
```
./gradlew sdk-api:bootRun
```

Go to
```
http://localhost:8080/swagger-ui.html
```

## Example cli
sdk-api/cli is a fork of [blockchain-ssm sdk cli](https://github.com/civis-blockchain/blockchain-ssm/tree/master/sdk/cli)
I have updated create, perform, register and start to request the REST API with curl

API don't allow to install and instantiate chaincode, it must be done with the docker client
  * Using the bclan network settings

```
# Use blockchain-ssm CLI SDK in blockchain-coop CLI environment
# Enter CLI environment
docker exec -it cli-bclan /bin/bash
export PATH=/opt/blockchain-coop/:$PATH
```

```
cli_ORGA=bc-coop.bclan
ORDERER_ADDR=orderer.bclan:7050
ORDERER_CERT=/etc/hyperledger/orderer/tlsca.bclan-cert.pem
CHANNEL=sandbox
CHAINCODE=ssm
VERSION=0.1.0
```

  * Install ssm chaincode

```
peer chaincode install -n ${CHAINCODE} -v ${VERSION} -p blockchain-coop/go/ssm/
```

  * Deploy ssm chaincode with admin "adam"

```
# Create keys for "adam"
rsa_keygen adam
# Create init.arg string
echo -n '{"Args":["init","[' > init.arg
json_agent adam adam.pub | jq . -cM | sed 's/"/\\"/g' | tr -d "\n" >> init.arg
echo -n ']"]}' >> init.arg
# Init chaincode
peer chaincode instantiate -o ${ORDERER_ADDR} --tls --cafile ${ORDERER_CERT} -C ${CHANNEL} -n ${CHAINCODE} -v ${VERSION} -c $(cat init.arg) -P "OR ('BlockchainLANCoopMSP.member')"
```

  * Go back to the host machine
```
exit
```

  * Check adam user has been created
```
curl -X GET "http://localhost:8080/ssm?args=adam&function=admin" -H  "accept: application/stream+json"
```

* Prepare sdk

```
cd sdk-api-rest
export PATH=`pwd`/cli:$PATH
mkdir example
cd example
```
Copy adam key pair in example

* Register users "bob" and "bam"
```
# Create keys
rsa_keygen bob
rsa_keygen sam
# Register users with "adam" for signer
register bob adam
register sam adam
```

```
curl -X GET "http://localhost:8080/ssm?args=bob&function=user" -H  "accept: application/stream+json"
curl -X GET "http://localhost:8080/ssm?args=sam&function=user" -H  "accept: application/stream+json"
```

  * Create the "Car dealership" ssm

```
echo '{
  "name": "Car dealership",
  "transitions": [
    {"from": 0, "to": 1, "role": "Seller", "action": "Sell"},
    {"from": 1, "to": 2, "role": "Buyer", "action": "Buy"}
  ]
}' > car_dealership.json
create car_dealership adam
```

```
curl -X GET "http://localhost:8080/ssm?args=Car%20dealership&function=ssm" -H  "accept: application/stream+json"

```

  * Start the "deal20181201" session

```
echo '{
  "ssm": "Car dealership",
  "session": "deal20181201",
  "public": "Used car for 100 dollars.",
  "roles": {
    "bob": "Buyer",
    "sam": "Seller"
  }
}' > deal20181201.json

start deal20181201 adam
```

```
curl -X GET "http://localhost:8080/ssm?args=deal20181201&function=session" -H  "accept: application/stream+json"
```

  * Perform transactions 

```
echo '{
  "session": "deal20181201",
  "public": "100 dollars 1978 Camaro",
  "iteration": 0
}' > state1.json
perform Sell state1 sam
```

```
curl -X GET "http://localhost:8080/ssm?args=deal20181201&function=session" -H  "accept: application/stream+json"
```

```
echo '{
  "session": "deal20181201",
  "public": "Deal !",
  "iteration": 1
}' > state2.json
perform Buy state2 bob
```

```
curl -X GET "http://localhost:8080/ssm?args=deal20181201&function=session" -H  "accept: application/stream+json"
```

