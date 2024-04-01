package com.pranshu.crudproject.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.google.gson.Gson;
import com.pranshu.crudproject.config.ClientCredentials;


@RestController
@CrossOrigin("http://frontend.route53testdemo.fun")
public class ClientCredentialsController {

  

    private static final String REGION = "us-west-2";
    private static final String SECRET_NAME = "keyCloak-credentials";


    private final Gson gson = new Gson();

    @GetMapping("/clientcredentials")
    public ClientCredentials getClientCredentials() {
        try {
            return fetchClientCredentials();
        } catch (Exception e) {
            // Log the error and return appropriate response
            e.printStackTrace();
            return null;
        }
    }

    private ClientCredentials fetchClientCredentials() {
        AWSSecretsManager client = buildSecretsManagerClient();
        String secretString = getSecretString(client);
        return gson.fromJson(secretString, ClientCredentials.class);
    }

    private AWSSecretsManager buildSecretsManagerClient() {
        return AWSSecretsManagerClientBuilder.standard()
                .withRegion(REGION)
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build();
    }

    private String getSecretString(AWSSecretsManager client) {
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(SECRET_NAME);
        GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        return getSecretValueResult.getSecretString();
    }
}
