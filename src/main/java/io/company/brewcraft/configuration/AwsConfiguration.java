package io.company.brewcraft.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;

import io.company.brewcraft.security.idp.AwsCognitoIdpClient;
import io.company.brewcraft.security.idp.AwsFactory;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.security.store.AwsSecretsManagerClient;
import io.company.brewcraft.security.store.SecretsManager;
import io.company.brewcraft.service.AwsArnMapper;
import io.company.brewcraft.service.AwsIamPolicyClient;
import io.company.brewcraft.service.AwsIamRoleClient;
import io.company.brewcraft.service.AwsIamRolePolicyAttachmentClient;
import io.company.brewcraft.service.AwsObjectStoreClient;
import io.company.brewcraft.service.IdpTenantUserMembershipRepository;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.impl.AwsIdpTenantUserMembershipRepository;
import io.company.brewcraft.service.impl.user.AwsIdpUserRepository;

@Configuration
public class AwsConfiguration {

    @Bean
    @ConditionalOnMissingBean(AwsFactory.class)
    public AwsFactory awsFactory() {
        return new AwsFactory();
    }

    @Bean
    @ConditionalOnMissingBean(AWSCognitoIdentityProvider.class)
    public AWSCognitoIdentityProvider awsCognitoIdpProvider(AwsFactory awsFactory, @Value("${aws.cognito.region}") final String cognitoRegion, @Value("${aws.cognito.url}") final String cognitoUrl,
            @Value("${aws.cognito.accessKey}") final String cognitoAccessKey, @Value("${aws.cognito.secretKey}") final String cognitoSecretKey) {
        return awsFactory.getIdentityProvider(cognitoRegion, cognitoUrl, cognitoAccessKey, cognitoSecretKey);
    }

    @Bean
    @ConditionalOnMissingBean(IdentityProviderClient.class)
    public IdentityProviderClient idpClient(AWSCognitoIdentityProvider awsIdpProvider, @Value("${aws.cognito.userPool.id}") final String cognitoUserPoolId) {
        return new AwsCognitoIdpClient(awsIdpProvider, cognitoUserPoolId);
    }

    @Bean
    @ConditionalOnMissingBean(IdpUserRepository.class)
    public IdpUserRepository idpUserRepo(IdentityProviderClient idpClient, IdpTenantUserMembershipRepository idpTenatUserRepo) {
        return new AwsIdpUserRepository(idpClient, idpTenatUserRepo);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public IdpTenantUserMembershipRepository idpTenatUserRepo(IdentityProviderClient idpClient) {
        return new AwsIdpTenantUserMembershipRepository(idpClient);
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

    @Bean
    @ConditionalOnMissingBean(AmazonS3.class)
    public AmazonS3 s3Client(AwsFactory awsFactory, @Value("${aws.s3.region}") String region, @Value("${aws.s3.accessKey}") String s3AccessKey, @Value("${aws.s3.accessSecret}") String s3Secret) {
        return awsFactory.s3Client(region, s3AccessKey, s3Secret);
    }

    @Bean
    @ConditionalOnMissingBean(AmazonIdentityManagement.class)
    public AmazonIdentityManagement iamClient(AwsFactory awsFactory, @Value("${aws.iam.region}") String region, @Value("${aws.iam.accessKey}") String iamAccessKey, @Value("${aws.iam.accessSecret}") String iamSecret) {
        return awsFactory.iamClient(region, iamAccessKey, iamSecret);
    }

    @Bean
    @ConditionalOnMissingBean(AmazonCognitoIdentity.class)
    public AmazonCognitoIdentity cognitoIdentity(AwsFactory awsFactory, @Value("${aws.cognito.region}") String region, @Value("${aws.cognito.accessKey}") final String accessKey, @Value("${aws.cognito.secretKey}") final String accessSecret) {
        return awsFactory.getAwsCognitoIdentityClient(region, accessKey, accessSecret);
    }

    @Bean
    @ConditionalOnMissingBean(AwsObjectStoreClient.class)
    public AwsObjectStoreClient awsObjectStoreClient(AmazonS3 s3Client) {
        return new AwsObjectStoreClient(s3Client);
    }

    @Bean
    @ConditionalOnMissingBean(AwsIamPolicyClient.class)
    public AwsIamPolicyClient awsIamPolicyClient(AmazonIdentityManagement iamClient, AwsArnMapper arnMapper) {
        return new AwsIamPolicyClient(iamClient, arnMapper);
    }

    @Bean
    @ConditionalOnMissingBean(AwsIamRoleClient.class)
    public AwsIamRoleClient awsIamRoleClientClient(AmazonIdentityManagement iamClient) {
        return new AwsIamRoleClient(iamClient);
    }

    @Bean
    @ConditionalOnMissingBean()
    public AwsIamRolePolicyAttachmentClient awsIamRolePolicyClientClient(AmazonIdentityManagementClient iamClient, AwsArnMapper arnMapper) {
        return new AwsIamRolePolicyAttachmentClient(iamClient, arnMapper);
    }
}
