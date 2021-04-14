package io.company.brewcraft.security.idp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdpConfiguration {

    @Bean
    @ConditionalOnMissingBean(AwsCognitoIdentityProviderFactory.class)
    public AwsCognitoIdentityProviderFactory awsCognitoIdentityProviderFactory() {
        return new AwsCognitoIdentityProviderFactoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(IdentityProvider.class)
    public IdentityProvider identityProvider(@Value("${aws.cognito.region}") final String cognitoRegion, @Value("${aws.cognito.url}") final String cognitoUrl, @Value("${aws.cognito.accessKey}") final String cognitoAccessKey, @Value("${aws.cognito.secretKey}") final String cognitoSecretKey, @Value("${aws.cognito.userPoolId}") final String cognitoUserPoolId) {
        return new AwsCognitoIdentityProviderClient(cognitoRegion, cognitoUrl, cognitoAccessKey, cognitoSecretKey, cognitoUserPoolId, awsCognitoIdentityProviderFactory());
    }
}
