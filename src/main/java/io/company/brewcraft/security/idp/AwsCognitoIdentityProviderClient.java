package io.company.brewcraft.security.idp;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.MessageActionType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class AwsCognitoIdentityProviderClient implements IdentityProviderClient {

    public static final Logger logger = LoggerFactory.getLogger(AwsCognitoIdentityProviderClient.class);

    private final AWSCognitoIdentityProvider aWSCognitoIdentityProvider;

    private final String userPoolId;

    public AwsCognitoIdentityProviderClient(final String cognitoRegion, final String cognitoUrl, final String cognitoAccessKey, final String cognitoSecretKey, final String userPoolId) {
        aWSCognitoIdentityProvider = createAwsCognitoIdentityProvider(cognitoRegion, cognitoUrl, cognitoAccessKey, cognitoSecretKey);
        this.userPoolId = userPoolId;
    }

    private AWSCognitoIdentityProvider createAwsCognitoIdentityProvider(final String cognitoRegion, String cognitoUrl, String cognitoAccessKey, String cognitoSecretKey) {
        logger.debug("Creating an instance of AWSCognitoIdentityProvider");
        final AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(cognitoUrl, cognitoRegion);
        final AWSCognitoIdentityProviderClientBuilder identityProviderClientBuilder = AWSCognitoIdentityProviderClient.builder().withEndpointConfiguration(endpointConfig);
        if (StringUtils.isNotEmpty(cognitoAccessKey) && StringUtils.isNotEmpty(cognitoSecretKey)) {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(cognitoAccessKey, cognitoSecretKey);
            identityProviderClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials));
        }
        return identityProviderClientBuilder.build();
    }

    @Override
    public void createUser(final String userName, final List<UserAttributeType> userAttributeTypeList) {
        final List<AttributeType> attributeTypes = userAttributeTypeList.stream().map(userAttributeType -> getAttribute(userAttributeType.getName(), userAttributeType.getValue())).collect(Collectors.toList());
        final AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest().withUserPoolId(userPoolId).withUsername(userName).withMessageAction(MessageActionType.SUPPRESS).withUserAttributes(attributeTypes);
        logger.debug("Attempting to save user {} in cognito user pool {} ", userName, userPoolId);
        final AdminCreateUserResult adminCreateUserResult = aWSCognitoIdentityProvider.adminCreateUser(adminCreateUserRequest);
        logger.debug("Successfully Saved user {} in cognito user pool {} with status : {}", userName, userPoolId, adminCreateUserResult.getUser().getUserStatus());
    }

    @Override
    public void updateUser(final String userName, final List<UserAttributeType> userAttributeTypeList) {
        final List<AttributeType> attributeTypes = userAttributeTypeList.stream().map(userAttributeType -> getAttribute(userAttributeType.getName(), userAttributeType.getValue())).collect(Collectors.toList());
        final AdminUpdateUserAttributesRequest adminUpdateUserRequest = new AdminUpdateUserAttributesRequest().withUserPoolId(userPoolId).withUsername(userName).withUserAttributes(attributeTypes);
        logger.debug("Attempting to update user {} in cognito user pool {} ", userName, userPoolId);
        aWSCognitoIdentityProvider.adminUpdateUserAttributes(adminUpdateUserRequest);
        logger.debug("Successfully Updated user {} in cognito user pool {}", userName, userPoolId);
    }


    @Override
    public void deleteUser(final String userName) {
        final AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest().withUserPoolId(userPoolId).withUsername(userName);
        logger.debug("Attempting to delete user {} in cognito user pool {} ", userName, userPoolId);
        aWSCognitoIdentityProvider.adminDeleteUser(adminDeleteUserRequest);
        logger.debug("Successfully deleted user {} from cognito user pool {}", userName, userPoolId);
    }

    private AttributeType getAttribute(final String name, final String value) {
        return new AttributeType().withName(name).withValue(value);
    }
}
