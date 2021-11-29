package com.fabric.sdk;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;

public class SdkMain {

    private static final String keyFolderPath="D:\\java_project\\fabricsdk\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\keystore";

    private static final String keyFileName="9acc63c07d14cf82e9e2724268ad4f76c7a89d0f4366059e76f8b1c02ba8a42b_sk";

    private static final String certFoldePath="D:\\java_project\\fabricsdk\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\admincerts";

    private static final String certFileName="Admin@org1.example.com-cert.pem";


    private static final String tlsOrderFilePath="D:\\java_project\\fabricsdk\\src\\main\\resources\\crypto-config\\ordererOrganizations\\example.com\\tlsca\\tlsca.example.com-cert.pem";

    private static final String tlsPeerFilePath="D:\\java_project\\fabricsdk\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\peers\\peer0.org1.example.com\\msp\\tlscacerts\\tlsca.org1.example.com-cert.pem";

    /*

    configtxgen -profile TwoOrgsChannel -outputCreateChannelTx channel-artifacts/test.tx -channelID test
    * */
    private static final String txFilePath="D:\\java_project\\fabricsdk\\src\\main\\resources\\test.tx";

    public static void main(String[] args) throws Exception{

        UserContext userContext=new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("darren");
        userContext.setName("admin");

        Enrollment enrollment=UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);

        userContext.setEnrollment(enrollment);


        FabricClient fabricClient=new FabricClient(userContext);



//已经有channel
//        Channel channel=fabricClient.getChannel("test");
//
//        channel.addOrderer(fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath));
//
//        channel.joinPeer(fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath));
//
//        channel.initialize();


 // 没有channel

//        Channel  channel=    fabricClient.createChannel("test",fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath),txFilePath);
//
//        channel.addOrderer(fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath));
//
//        channel.joinPeer(fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath));
//
//        channel.initialize();

    }
}
