package com.fabric.sdk;


import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.util.Properties;

public class FabricCAClient {

    private HFCAClient hfcaClient;

    public FabricCAClient(String url, Properties properties) throws Exception{

      hfcaClient =HFCAClient.createNewInstance(url,properties);
        CryptoSuite cryptoSuite=CryptoSuite.Factory.getCryptoSuite();
        hfcaClient.setCryptoSuite(cryptoSuite);

    }

    //注册用户

    public String register(UserContext registar,UserContext register) throws Exception{
        RegistrationRequest registrationRequest=new RegistrationRequest(register.getName(),register.getAffiliation());
        String secret=hfcaClient.register(registrationRequest,registar);
        return secret;
    }

    // 登录用户
    public Enrollment enroll(String username,String password) throws Exception{
        Enrollment enrollment=hfcaClient.enroll(username,password);
        return enrollment;
    }
}
