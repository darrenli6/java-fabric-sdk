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

    // 合约实例化

    public void initChaincode(String channelName,TransactionRequest.Type lang,
                              String chaincodeName,String chaincodeVersion,
                              Orderer orderer,Peer peer,String funcName,String args[]) throws Exception{

        // 初始化通道
        Channel channel=getChannel(channelName);
        channel.addPeer(peer);
        channel.addOrderer(orderer);
        channel.initialize();

        // 提案
        InstantiateProposalRequest instantiateProposalRequest=hfClient.newInstantiationProposalRequest();
        instantiateProposalRequest.setArgs(args);
        instantiateProposalRequest.setFcn(funcName);
        instantiateProposalRequest.setChaincodeLanguage(lang);

        ChaincodeID.Builder builder= ChaincodeID.newBuilder().setName(chaincodeName).setVersion(chaincodeVersion);
        instantiateProposalRequest.setChaincodeID(builder.build());
        Collection<ProposalResponse> responses=channel.sendInstantiationProposal(instantiateProposalRequest);

        for (ProposalResponse response:responses){
            if(response.getStatus().getStatus()==200){
                log.info("{} init success",response.getPeer().getName());
            }else{
                log.info("{} init fail",response.getMessage());
            }
        }
        channel.sendTransaction(responses);

    }

    // 升级实例化

    public void upgradeChaincode(String channelName,TransactionRequest.Type lang,
                              String chaincodeName,String chaincodeVersion,
                              Orderer orderer,Peer peer,String funcName,String args[]) throws Exception{

        // 初始化通道
        Channel channel=getChannel(channelName);
        channel.addPeer(peer);
        channel.addOrderer(orderer);
        channel.initialize();

        // 升级提案
        UpgradeProposalRequest upgradeProposalRequest=hfClient.newUpgradeProposalRequest();
        upgradeProposalRequest.setArgs(args);
        upgradeProposalRequest.setFcn(funcName);
        upgradeProposalRequest.setChaincodeLanguage(lang);

        // 背书策略
        ChaincodeEndorsementPolicy chaincodeEndorsementPolicy=new ChaincodeEndorsementPolicy();
        chaincodeEndorsementPolicy.fromYamlFile(new File("D:\\java_project\\fabricsdk\\src\\main\\resources\\policy\\chaincodeendorsementpolicy.yaml"));
        upgradeProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);

        ChaincodeID.Builder builder= ChaincodeID.newBuilder().setName(chaincodeName).setVersion(chaincodeVersion);
        upgradeProposalRequest.setChaincodeID(builder.build());
        Collection<ProposalResponse> responses=channel.sendUpgradeProposal(upgradeProposalRequest);

        for (ProposalResponse response:responses){
            if(response.getStatus().getStatus()==200){
                log.info("{} init success",response.getPeer().getName());
            }else{
                log.info("{} init fail",response.getMessage());
            }
        }
        channel.sendTransaction(responses);

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
