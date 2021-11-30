package com.fabric.sdk;

import org.hyperledger.fabric.sdk.*;

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
                "3.0","C:\\Users\\darren\\Downloads\\fabric-hospital-master",
                "basicinfo",peers);



    }

    // 实话化合约

    public void instanceChain() throws Exception{

        UserContext userContext=new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("darren");
        userContext.setName("admin");

        Enrollment enrollment=UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);

        userContext.setEnrollment(enrollment);


        FabricClient fabricClient=new FabricClient(userContext);



        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
        Orderer orderer = fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath);
        String initArgs[]={""};
        fabricClient.initChaincode("mychannel",TransactionRequest.Type.GO_LANG,
                "basicinfo",
                "3.0",orderer,peer0,
                "init",initArgs);

/*
root@fab6e389b323:/opt/gopath/src/github.com/hyperledger/fabric/peer# peer chaincode list --instantiated -C mychannel
Get instantiated chaincodes on channel mychannel:
Name: basicinfo, Version: 2.0, Path: basicinfo, Escc: escc, Vscc: vscc
Name: mycc, Version: 1.0, Path: github.com/chaincode/chaincode_example02/go/, Escc: escc, Vscc: vscc
*
* */

    }


    // 升级合约

    public void upgradeChain() throws Exception{

        UserContext userContext=new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("darren");
        userContext.setName("admin");

        Enrollment enrollment=UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);

        userContext.setEnrollment(enrollment);


        FabricClient fabricClient=new FabricClient(userContext);



        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
        Orderer orderer = fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath);
        String initArgs[]={""};
        fabricClient.upgradeChaincode("mychannel",TransactionRequest.Type.GO_LANG,
                "basicinfo",
                "3.0",orderer,peer0,
                "init",initArgs);
        // 调试 查看order和peer节点日志
        // 先安装3.0 然后进行升级，不必初始化 ，可以直接将2.0覆盖


    }

    public static void main(String[] args) throws Exception{

        SdkMain sdkMain=new SdkMain();
        sdkMain.upgradeChain();
//        sdkMain.instanceChain();
//        sdkMain.installChain();
    }
}
