package io.company.brewcraft.configuration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.GroupType;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.BaseIaasObjectStoreFile;
import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.BaseIaasUser;
import io.company.brewcraft.model.BaseIaasUserTenantMembership;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasObjectStoreFile;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRepositoryProvider;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasUser;
import io.company.brewcraft.model.IaasUserTenantMembership;
import io.company.brewcraft.model.IaasUserTenantMembershipId;
import io.company.brewcraft.model.TenantContextAwsObjectStoreFileClientProvider;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.model.UpdateIaasObjectStore;
import io.company.brewcraft.model.UpdateIaasObjectStoreFile;
import io.company.brewcraft.model.UpdateIaasPolicy;
import io.company.brewcraft.model.UpdateIaasRole;
import io.company.brewcraft.model.UpdateIaasUser;
import io.company.brewcraft.model.UpdateIaasUserTenantMembership;
import io.company.brewcraft.security.idp.AwsCognitoIdpClient;
import io.company.brewcraft.security.idp.AwsFactory;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.security.store.AwsSecretsManagerClient;
import io.company.brewcraft.security.store.SecretsManager;
import io.company.brewcraft.service.AwsArnMapper;
import io.company.brewcraft.service.AwsIaasObjectStoreMapper;
import io.company.brewcraft.service.AwsIaasPolicyMapper;
import io.company.brewcraft.service.AwsIaasRoleMapper;
import io.company.brewcraft.service.AwsIamPolicyClient;
import io.company.brewcraft.service.AwsIamRoleClient;
import io.company.brewcraft.service.AwsIamRolePolicyAttachmentClient;
import io.company.brewcraft.service.AwsObjectStoreClient;
import io.company.brewcraft.service.IaasClient;
import io.company.brewcraft.service.IaasEntityMapper;
import io.company.brewcraft.service.IaasRoleService;
import io.company.brewcraft.service.IdpTenantUserMembershipRepository;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.TenantContextIaasAuthorizationFetch;
import io.company.brewcraft.service.TenantContextIaasObjectStoreNameProvider;
import io.company.brewcraft.service.impl.AwsIaasUserTenantMembershipClient;
import io.company.brewcraft.service.impl.AwsIdpTenantWithRoleClient;
import io.company.brewcraft.service.impl.user.AwsCognitoUserClient;
import io.company.brewcraft.service.impl.user.AwsIdpUserRepository;
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
            @Value("${aws.cognito.access-key}") final String cognitoAccessKey, @Value("${aws.cognito.access-secret}") final String cognitoAccessSecret) {
        return awsFactory.getIdentityProvider(cognitoRegion, cognitoUrl, cognitoAccessKey, cognitoAccessSecret);
    }

    @Bean
    @ConditionalOnMissingBean(IdentityProviderClient.class)
    public IdentityProviderClient idpClient(AWSCognitoIdentityProvider awsIdpProvider, @Value("${aws.cognito.user-pool.id}") final String cognitoUserPoolId) {
        return new AwsCognitoIdpClient(awsIdpProvider, cognitoUserPoolId);
    }

    @Bean
    @ConditionalOnMissingBean(IdpUserRepository.class)
    public IdpUserRepository idpUserRepo(IdentityProviderClient idpClient, IdpTenantUserMembershipRepository idpTenatUserRepo) {
        return new AwsIdpUserRepository(idpClient, idpTenatUserRepo);
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
    public AmazonS3 s3Client(AwsFactory awsFactory, @Value("${aws.s3.region}") String region, @Value("${aws.s3.access-key}") String s3AccessKey, @Value("${aws.s3.access-secret}") String s3Secret) {
        return awsFactory.s3Client(region, s3AccessKey, s3Secret);
    }

    @Bean
    @ConditionalOnMissingBean(AmazonIdentityManagement.class)
    public AmazonIdentityManagement iamClient(AwsFactory awsFactory, @Value("${aws.iam.region}") String region, @Value("${aws.iam.access-key}") String iamAccessKey, @Value("${aws.iam.access-secret}") String iamSecret) {
        return awsFactory.iamClient(region, iamAccessKey, iamSecret);
    }

    @Bean
    @ConditionalOnMissingBean(AmazonCognitoIdentity.class)
    public AmazonCognitoIdentity cognitoIdentity(AwsFactory awsFactory, @Value("${aws.cognito.region}") String region, @Value("${aws.cognito.access-key}") final String accessKey, @Value("${aws.cognito.access-secret}") final String accessSecret) {
        return awsFactory.getAwsCognitoIdentityClient(region, accessKey, accessSecret);
    }

    @Bean
    @ConditionalOnMissingBean(AwsIamRolePolicyAttachmentClient.class)
    public AwsIamRolePolicyAttachmentClient awsIamRolePolicyClientClient(AmazonIdentityManagementClient iamClient, AwsArnMapper arnMapper) {
        return new AwsIamRolePolicyAttachmentClient(iamClient, arnMapper);
    }
    
    @Bean
    public IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> awsIdpTenantClient(AWSCognitoIdentityProvider idp, String userPoolId, IaasEntityMapper<GroupType, IaasIdpTenant> mapper, AwsArnMapper arnMapper, IaasRoleService roleService) {
        return new AwsIdpTenantWithRoleClient(idp, userPoolId, mapper, arnMapper, roleService);
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
    public IaasClient<String, IaasUser, BaseIaasUser, UpdateIaasUser> awsUserClient(AWSCognitoIdentityProvider idp, String userPoolId) {
        return new AwsCognitoUserClient(idp, userPoolId, AwsCognitoAdminGetUserResultMapper.INSTANCE, AwsCognitoUserMapper.INSTANCE);
    }
    
    @Bean
    public IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> awsCognitoUserGroupMembership(AWSCognitoIdentityProvider idp, String userPoolId) {
        return new AwsIaasUserTenantMembershipClient(idp, userPoolId, AwsGroupTypeMapper.INSTANCE);
    }
    
    @Bean
    public IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> iaasObjectStoreFileClientProvider(@Value("${aws.s3.region}") String region, TenantContextIaasObjectStoreNameProvider bucketNameProvider, TenantContextIaasAuthorizationFetch authFetcher, AwsFactory awsFactory, Long getPresignUrlDuration) {
        return new TenantContextAwsObjectStoreFileClientProvider(region, bucketNameProvider, authFetcher, awsFactory, getPresignUrlDuration);
    }
}
