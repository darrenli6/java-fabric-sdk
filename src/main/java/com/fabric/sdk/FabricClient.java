package com.fabric.sdk;

import org.apache.commons.logging.Log;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class FabricClient {


    private static final Logger log = LoggerFactory.getLogger(FabricClient.class);

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


    // 安装合约
    public void installChaincode(TransactionRequest.Type lang,
                                 String chaincodeName,
                                 String chaincodeVersion,
                                 String chaincodeLocation,
                                 String chaincodePath, List<Peer> peers
                                 ) throws Exception{

        InstallProposalRequest installProposalRequest=hfClient.newInstallProposalRequest();
        ChaincodeID.Builder builder=ChaincodeID.newBuilder().setName(chaincodeName).setVersion(chaincodeVersion);

        installProposalRequest.setChaincodeLanguage(lang);
        installProposalRequest.setChaincodeID(builder.build());
        installProposalRequest.setChaincodeSourceLocation(new File(chaincodeLocation));

        installProposalRequest.setChaincodePath(chaincodePath);

        Collection<ProposalResponse> responses=hfClient.sendInstallProposal(installProposalRequest,peers);

        for (ProposalResponse response: responses){
            if(response.getStatus().getStatus()==200){
                 log.info("{} installed success",response.getPeer().getName());
            }else{
                log.error("{}  installed failed  ",response.getMessage());
            }
        }

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
