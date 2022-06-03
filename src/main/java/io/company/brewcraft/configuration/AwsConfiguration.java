package io.company.brewcraft.configuration;

import java.net.URI;

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

import io.company.brewcraft.model.AwsDocumentTemplates;
import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasObjectStoreFile;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.BaseIaasUser;
import io.company.brewcraft.model.BaseIaasUserTenantMembership;
import io.company.brewcraft.model.IaasBucketCorsConfiguration;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasObjectStoreFile;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRepositoryProvider;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;
import io.company.brewcraft.model.IaasUser;
import io.company.brewcraft.model.IaasUserTenantMembership;
import io.company.brewcraft.model.IaasUserTenantMembershipId;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.model.UpdateIaasObjectStore;
import io.company.brewcraft.model.UpdateIaasObjectStoreFile;
import io.company.brewcraft.model.UpdateIaasPolicy;
import io.company.brewcraft.model.UpdateIaasRole;
import io.company.brewcraft.model.UpdateIaasRolePolicyAttachment;
import io.company.brewcraft.model.UpdateIaasUser;
import io.company.brewcraft.model.UpdateIaasUserTenantMembership;
import io.company.brewcraft.security.idp.AwsFactory;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.store.AwsSecretsManagerClient;
import io.company.brewcraft.security.store.SecretsManager;
import io.company.brewcraft.service.AwsArnMapper;
import io.company.brewcraft.service.AwsCognitoIdentityClient;
import io.company.brewcraft.service.AwsCognitoIdentitySdkWrapper;
import io.company.brewcraft.service.AwsCorsConfigClient;
import io.company.brewcraft.service.AwsIaasObjectStoreMapper;
import io.company.brewcraft.service.AwsIaasPolicyMapper;
import io.company.brewcraft.service.AwsIaasRoleMapper;
import io.company.brewcraft.service.AwsIamPolicyClient;
import io.company.brewcraft.service.AwsIamRoleClient;
import io.company.brewcraft.service.AwsIamRolePolicyAttachmentClient;
import io.company.brewcraft.service.AwsIdentityCredentialsMapper;
import io.company.brewcraft.service.AwsObjectStoreClient;
import io.company.brewcraft.service.AwsResourceCredentialsFetcher;
import io.company.brewcraft.service.AwsTenantIaasResourceBuilder;
import io.company.brewcraft.service.CachedAwsCognitoIdentityClient;
import io.company.brewcraft.service.IaasAuthorizationFetch;
import io.company.brewcraft.service.IaasClient;
import io.company.brewcraft.service.IaasRoleService;
import io.company.brewcraft.service.TenantContextAwsBucketNameProvider;
import io.company.brewcraft.service.TenantContextAwsObjectStoreFileClientProvider;
import io.company.brewcraft.service.TenantContextIaasAuthorizationFetch;
import io.company.brewcraft.service.TenantContextIaasObjectStoreNameProvider;
import io.company.brewcraft.service.TenantIaasResourceBuilder;
import io.company.brewcraft.service.impl.AwsIaasUserTenantMembershipClient;
import io.company.brewcraft.service.impl.AwsIdpTenantWithRoleClient;
import io.company.brewcraft.service.impl.user.AwsCognitoUserClient;
import io.company.brewcraft.service.mapper.AwsCognitoAdminGetUserResultMapper;
import io.company.brewcraft.service.mapper.AwsCognitoUserMapper;
import io.company.brewcraft.service.mapper.AwsGroupTypeMapper;

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
            @Value("${aws.cognito.access-key}") final String cognitoAccessKeyId, @Value("${aws.cognito.access-secret}") final String cognitoAccessSecretKey) {
        return awsFactory.getIdentityProvider(cognitoRegion, cognitoUrl, cognitoAccessKeyId, cognitoAccessSecretKey);
    }

    @Bean
    @ConditionalOnMissingBean(AWSSecretsManager.class)
    public AWSSecretsManager awsSecretsMgr(AwsFactory awsFactory, @Value("${aws.secretsmanager.region}") String region, @Value("${aws.secretsmanager.url}") String url, @Value("${aws.secretsmanager.access-key}") String secretsManagerKey, @Value("${aws.secretsmanager.access-secret-key}") String secretsManagerSecretKey) {
        return awsFactory.secretsMgrClient(region, url, secretsManagerKey, secretsManagerSecretKey);
    }

    @Bean
    @ConditionalOnMissingBean(AmazonS3.class)
    public AmazonS3 s3Client(AwsFactory awsFactory, @Value("${aws.s3.region}") String region, @Value("${aws.s3.access-key}") String s3AccessKeyId, @Value("${aws.s3.access-secret}") String s3Secret) {
        return awsFactory.s3Client(region, s3AccessKeyId, s3Secret);
    }

    @Bean
    @ConditionalOnMissingBean(AmazonIdentityManagement.class)
    public AmazonIdentityManagement iamClient(AwsFactory awsFactory, @Value("${aws.iam.access-key}") String iamAccessKeyId, @Value("${aws.iam.access-secret}") String iamSecret) {
        return awsFactory.iamClient(iamAccessKeyId, iamSecret);
    }

    @Bean
    @ConditionalOnMissingBean(AmazonCognitoIdentity.class)
    public AmazonCognitoIdentity cognitoIdentity(AwsFactory awsFactory, @Value("${aws.cognito.region}") String region, @Value("${aws.cognito.access-key}") final String accessKeyId, @Value("${aws.cognito.access-secret}") final String accessSecretKey) {
        return awsFactory.getAwsCognitoIdentityClient(region, accessKeyId, accessSecretKey);
    }

    @Bean
    @ConditionalOnMissingBean(AwsArnMapper.class)
    public AwsArnMapper arnMapper(@Value("${aws.deployment.accountId}") String accountId, @Value("${aws.deployment.parition}") String partition) {
        return new AwsArnMapper(accountId, partition);
    }

    @Bean
    @ConditionalOnMissingBean(AwsDocumentTemplates.class)
    public AwsDocumentTemplates awsDocumentTemplates(@Value("${aws.cognito.identity.pool.id}") String cognitoIdPoolId) {
        return new AwsDocumentTemplates(cognitoIdPoolId);
    }

    @Bean
    @ConditionalOnMissingBean(SecretsManager.class)
    public SecretsManager<String, String> secretManager(AWSSecretsManager awsSecretsMgr) {
        return new AwsSecretsManagerClient(awsSecretsMgr);
    }

    @Bean
    @ConditionalOnMissingBean(AwsIamRolePolicyAttachmentClient.class)
    public IaasClient<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment, UpdateIaasRolePolicyAttachment> awsIamRolePolicyClientClient(AmazonIdentityManagementClient iamClient, AwsArnMapper arnMapper) {
        return new AwsIamRolePolicyAttachmentClient(iamClient, arnMapper);
    }

    @Bean
    public IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> awsIdpTenantClient(AWSCognitoIdentityProvider idp, @Value("${aws.cognito.user-pool.id}") String userPoolId, AwsArnMapper arnMapper, IaasRoleService roleService) {
        return new AwsIdpTenantWithRoleClient(idp, userPoolId, AwsGroupTypeMapper.INSTANCE, arnMapper, roleService);
    }

    @Bean
    public IaasClient<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> awsIamPolicyClient(AmazonIdentityManagement awsIamClient, AwsArnMapper awsMapper) {
        return new AwsIamPolicyClient(awsIamClient, awsMapper, AwsIaasPolicyMapper.INSTANCE);
    }

    @Bean
    public IaasClient<String, IaasRole, BaseIaasRole, UpdateIaasRole> awsIamRoleClient(AmazonIdentityManagement awsIamClient) {
        return new AwsIamRoleClient(awsIamClient, AwsIaasRoleMapper.INSTANCE);
    }

    @Bean
    public IaasClient<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> awsObjectStoreClient(AmazonS3 awsClient) {
        return new AwsObjectStoreClient(awsClient, AwsIaasObjectStoreMapper.INSTANCE);
    }

    @Bean
    public IaasClient<String, IaasUser, BaseIaasUser, UpdateIaasUser> awsUserClient(AWSCognitoIdentityProvider idp, @Value("${aws.cognito.user-pool.id}") String userPoolId) {
        return new AwsCognitoUserClient(idp, userPoolId, AwsCognitoAdminGetUserResultMapper.INSTANCE, AwsCognitoUserMapper.INSTANCE);
    }

    @Bean
    public IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> awsCognitoUserGroupMembership(AWSCognitoIdentityProvider idp, @Value("${aws.cognito.user-pool.id}") String userPoolId) {
        return new AwsIaasUserTenantMembershipClient(idp, userPoolId, AwsGroupTypeMapper.INSTANCE);
    }

    @Bean
    @ConditionalOnMissingBean(AwsCognitoIdentityClient.class)
    public AwsCognitoIdentityClient cognitoIdentityClient(AmazonCognitoIdentity cognitoIdentity, @Value("${app.iaas.credentials.expiry.duration}") long credentialsExpiryDurationSeconds) {
        AwsCognitoIdentityClient cognitoIdentityClient = new AwsCognitoIdentitySdkWrapper(cognitoIdentity);

        return new CachedAwsCognitoIdentityClient(cognitoIdentityClient, credentialsExpiryDurationSeconds);
    }

    @Bean
    @ConditionalOnMissingBean(IaasAuthorizationFetch.class)
    public IaasAuthorizationFetch iaasAuthorizationFetch(AwsCognitoIdentityClient identityClient, @Value("${aws.cognito.user-pool.url}") String userPoolUrl) {
        return new AwsResourceCredentialsFetcher(identityClient, AwsIdentityCredentialsMapper.INSTANCE, userPoolUrl);
    }

    @Bean
    @ConditionalOnMissingBean(IaasRepositoryProvider.class)
    public IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> iaasObjectStoreFileClientProvider(@Value("${aws.s3.region}") String region, TenantContextIaasObjectStoreNameProvider bucketNameProvider, TenantContextIaasAuthorizationFetch authFetcher, AwsFactory awsFactory, @Value("${app.object-store.file.get.url.expiry}") Long getPresignUrlDuration) {
        return new TenantContextAwsObjectStoreFileClientProvider(region, bucketNameProvider, authFetcher, awsFactory, getPresignUrlDuration);
    }

    @Bean
    @ConditionalOnMissingBean(TenantIaasResourceBuilder.class)
    public TenantIaasResourceBuilder resourceBuilder(AwsDocumentTemplates templates) {
        return new AwsTenantIaasResourceBuilder(templates);
    }

    @Bean
    @ConditionalOnMissingBean(TenantContextIaasObjectStoreNameProvider.class)
    public TenantContextIaasObjectStoreNameProvider objectStoreNameProvider(AwsDocumentTemplates awsTemplates, ContextHolder contextHolder, @Value("app.object-store.bucket.name") String appBucketName) {
        return new TenantContextAwsBucketNameProvider(awsTemplates, contextHolder, appBucketName);
    }

    @Bean
    public IaasClient<String, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration> iaasObjectStoreCorsConfigClient(AmazonS3 awsS3Client) {
        return new AwsCorsConfigClient(awsS3Client);
    }
}
