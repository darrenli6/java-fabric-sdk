package com.fabric.sdk;

import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.File;
import java.util.Properties;

public class FabricClient {

    private HFClient hfClient;

    public FabricClient(UserContext userContext) throws Exception{
        hfClient=HFClient.createNewInstance();
        //这里设置加密算法，如果服务端修改了加密，这里也需要修改
        CryptoSuite cryptoSuite=CryptoSuite.Factory.getCryptoSuite();
        hfClient.setCryptoSuite(cryptoSuite);

        hfClient.setUserContext(userContext);



    }
    // channel 名称 order节点
    public Channel createChannel(String channelName, Orderer orderer,String txPath) throws Exception{

        ChannelConfiguration channelConfiguration=new ChannelConfiguration(new File(txPath));

        return hfClient.newChannel(channelName,orderer,channelConfiguration,hfClient.getChannelConfigurationSignature(channelConfiguration,hfClient.getUserContext()));



    }

    public Orderer getOrderer(String name,String grpcUrl,String tlsFilePath) throws  Exception{

        Properties properties=new Properties();
        properties.setProperty("pemFile",tlsFilePath);
        Orderer orderer=hfClient.newOrderer(name,grpcUrl,properties);
        return orderer;
    }

    public Peer getPeer(String name, String grpcUrl, String tlsFilePath) throws  Exception{

        Properties properties=new Properties();
        properties.setProperty("pemFile",tlsFilePath);
        Peer peer=hfClient.newPeer(name,grpcUrl,properties);
        return peer;
    }

    public Channel getChannel(String channelName) throws  Exception{


        Channel channel=hfClient.newChannel(channelName);

        return channel;


    }
}
