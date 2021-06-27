package io.company.brewcraft.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;

import io.company.brewcraft.security.idp.AwsCognitoIdpClient;
import io.company.brewcraft.security.idp.AwsFactory;
import io.company.brewcraft.security.idp.AwsFactoryImpl;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.security.store.AwsSecretsManagerClient;
import io.company.brewcraft.security.store.SecretsManager;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.impl.user.AwsIdpUserRepository;

@Configuration
public class AwsConfiguration {

    @Bean
    @ConditionalOnMissingBean(AwsFactory.class)
    public AwsFactory awsFactory() {
        return new AwsFactoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(AWSCognitoIdentityProvider.class)
    public AWSCognitoIdentityProvider awsCognitoIdpProvider(AwsFactory awsFactory, @Value("${aws.cognito.region}") final String cognitoRegion, @Value("${aws.cognito.url}") final String cognitoUrl, @Value("${aws.cognito.accessKey}") final String cognitoAccessKey, @Value("${aws.cognito.secretKey}") final String cognitoSecretKey) {
        return awsFactory.getIdentityProvider(cognitoRegion, cognitoUrl, cognitoAccessKey, cognitoSecretKey);
    }

    @Bean
    @ConditionalOnMissingBean(IdentityProviderClient.class)
    public IdentityProviderClient idpClient(AWSCognitoIdentityProvider awsIdpProvider, @Value("${aws.cognito.userPoolId}") final String cognitoUserPoolId) {
        return new AwsCognitoIdpClient(awsIdpProvider, cognitoUserPoolId);
    }

    @Bean
    @ConditionalOnMissingBean()
    public IdpUserRepository idpUserRepo(IdentityProviderClient idpClient) {
        return new AwsIdpUserRepository((AwsCognitoIdpClient) idpClient);
    }

    @Bean
    @ConditionalOnMissingBean(AWSSecretsManager.class)
    public AWSSecretsManager awsSecretsMgr(AwsFactory awsFactory, @Value("${aws.secretsmanager.region}") String region, @Value("${aws.secretsmanager.url}") String url) {
        return awsFactory.secretsMgrClient(region, url);
    }

    @Bean
    @ConditionalOnMissingBean(SecretsManager.class)
    public SecretsManager<String, String> secretManager(AWSSecretsManager awsSecretsMgr) {
        return new AwsSecretsManagerClient(awsSecretsMgr);
    }
}
