package com.pranshu.crudproject.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@Component
public class AppConfig {
	    
//    @Value("${Access_Key}")
//    private String accessKey;
//    @Value("${Secret_Access_Key}")
//    private String secretkey;

    private Gson gson = new Gson();
    @Bean
    public DataSource dataSource() {
        AwsSecrets secrets = getSecret();
        if (secrets != null) {
            return DataSourceBuilder
                .create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://" + secrets.getHost() + ":" + secrets.getPort() + "/demopost")
                .username(secrets.getUsername())
                .password(secrets.getPassword())
                .build();
        } else {
            // Handle the case where secrets could not be retrieved
           return null;
        }
    }

    private AwsSecrets getSecret() {
        String secretName = "db-credentials4";
        String region = "us-west-2";

        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
            .withRegion(region)
            .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
            .build();

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
            .withSecretId(secretName);

        GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        String secret = getSecretValueResult.getSecretString();
        
        if (secret != null) {
            return gson.fromJson(secret, AwsSecrets.class);
        } else {
            // Handle the case where secret string is null
            return null; // Or throw an exception if necessary
        }
    }

}
