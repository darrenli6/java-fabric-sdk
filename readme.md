 
### demo 

- fabric-sdk创建channel
- fabric-sdk安装合约
- fabric-sdk实例化合约
- fabric-sdk升级合约
- fabric-sdk触发合约
- fabric-sdk查询合约
- fabric-sdk操作fabric-ca
- fabric-sdk在fabric-ca中注册用户


### 用命令创建channel

``` 
configtxgen -profile TwoOrgsChannel -outputCreateChannelTx channel-artifacts/mychannel1.tx -channelID mychannel1

peer channel create -o orderer.example.com:7050 -c mychannel1 -f ./channel-artifacts/mychannel1.tx --tls --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem

peer channel join -b mychannel1.block

peer channel list
```


### 查看安装的channel 

```aidl

peer channel list
```

### 查看安装的chaincode 


```aidl

root@fab6e389b323:/opt/gopath/src/github.com/hyperledger/fabric/peer# peer chaincode list --installed 
Get installed chaincodes on peer:
Name: basicinfo, Version: 2.0, Path: basicinfo, Id: 131177c8cd9d094281c0bc485bb5d91f6244d098780740dbe9fbcb971b9b5884
Name: mycc, Version: 1.0, Path: github.com/chaincode/chaincode_example02/go/, Id: 333a19b11063d0ade7be691f9f22c04ad369baba15660f7ae9511fd1a6488209
```


### 实例化chaincode

过程：client 背书拿到提案，然后给order节点进行上块操作



```
root@fab6e389b323:/opt/gopath/src/github.com/hyperledger/fabric/peer# peer chaincode list --instantiated -C mychannel 
Get instantiated chaincodes on channel mychannel:
Name: mycc, Version: 1.0, Path: github.com/chaincode/chaincode_example02/go/, Escc: escc, Vscc: vscc
```

##### fabric-java-sdk使用说明

1、首先需要在操作系统的host文件里，配置fabric网络域名和ip的映射，
如下:192.168.x.x peer0.org1.example.com peer1.org1.example.com  ……
2、将网络中的证书拷贝到本地，以使用的时候读取证书，将代码中证书的目录进行修改。

 


