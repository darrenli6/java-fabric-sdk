### 用命令创建channel

``` 
configtxgen -profile TwoOrgsChannel -outputCreateChannelTx channel-artifacts/mychannel1.tx -channelID mychannel1

peer channel create -o orderer.example.com:7050 -c mychannel1 -f ./channel-artifacts/mychannel1.tx --tls --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem

peer channel join -b mychannel1.block

peer channel list
```



##### fabric-java-sdk使用说明

1、首先需要在操作系统的host文件里，配置fabric网络域名和ip的映射，
如下:192.168.x.x peer0.org1.example.com peer1.org1.example.com  ……
2、将网络中的证书拷贝到本地，以使用的时候读取证书，将代码中证书的目录进行修改。

 


