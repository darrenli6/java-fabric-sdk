package com.fabric.sdk;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.TransactionRequest;

import java.util.ArrayList;
import java.util.List;

public class SdkMain {

    private static final String keyFolderPath="D:\\java_project\\fabricsdk\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\keystore";

    private static final String keyFileName="9acc63c07d14cf82e9e2724268ad4f76c7a89d0f4366059e76f8b1c02ba8a42b_sk";

    private static final String certFoldePath="D:\\java_project\\fabricsdk\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\admincerts";

    private static final String certFileName="Admin@org1.example.com-cert.pem";


    private static final String tlsOrderFilePath="D:\\java_project\\fabricsdk\\src\\main\\resources\\crypto-config\\ordererOrganizations\\example.com\\tlsca\\tlsca.example.com-cert.pem";

    private static final String tlsPeerFilePath="D:\\java_project\\fabricsdk\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\peers\\peer0.org1.example.com\\msp\\tlscacerts\\tlsca.org1.example.com-cert.pem";

    private static final String tlsPeer1Org1FilePath="D:\\java_project\\fabricsdk\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\peers\\peer1.org1.example.com\\msp\\tlscacerts\\tlsca.org1.example.com-cert.pem";
    /*

    configtxgen -profile TwoOrgsChannel -outputCreateChannelTx channel-artifacts/test.tx -channelID test
    * */
    private static final String txFilePath="D:\\java_project\\fabricsdk\\src\\main\\resources\\test.tx";


    private void createChannel() throws Exception{
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

    public void installChain() throws Exception{

        UserContext userContext=new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("darren");
        userContext.setName("admin");

        Enrollment enrollment=UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);

        userContext.setEnrollment(enrollment);


        FabricClient fabricClient=new FabricClient(userContext);



        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
        Peer peer1 = fabricClient.getPeer("peer1.org1.example.com","grpcs://peer1.org1.example.com:8051",tlsPeer1Org1FilePath);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        fabricClient.installChaincode(TransactionRequest.Type.GO_LANG,
                "basicinfo",
                "2.0","C:\\Users\\darren\\Downloads\\fabric-hospital-master",
                "basicinfo",peers);



    }


    public static void main(String[] args) throws Exception{

        SdkMain sdkMain=new SdkMain();
        sdkMain.installChain();
    }
}
