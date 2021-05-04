package io.company.brewcraft.security.idp;

import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.MessageActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class AwsCognitoIdentityProviderClient implements IdentityProviderClient {

    public static final Logger logger = LoggerFactory.getLogger(AwsCognitoIdentityProviderClient.class);

    private final AWSCognitoIdentityProviderFactory aWSCognitoIdentityProviderFactory;

    private final String userPoolId;

    public AwsCognitoIdentityProviderClient(final AWSCognitoIdentityProviderFactory awsCognitoIdentityProviderFactory, final String userPoolId) {
        aWSCognitoIdentityProviderFactory = awsCognitoIdentityProviderFactory;
        this.userPoolId = userPoolId;
    }

    @Override
    public void createUser(final String userName, final List<UserAttributeType> userAttributeTypes) {
        final List<AttributeType> attributeTypes = userAttributeTypes.stream().map(userAttributeType -> getAttribute(userAttributeType.getName(), userAttributeType.getValue())).collect(Collectors.toList());
        final AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest().withUserPoolId(userPoolId).withUsername(userName).withMessageAction(MessageActionType.SUPPRESS).withUserAttributes(attributeTypes);
        logger.debug("Attempting to save user {} in cognito user pool {} ", userName, userPoolId);
        final AdminCreateUserResult adminCreateUserResult = aWSCognitoIdentityProviderFactory.getInstance().adminCreateUser(adminCreateUserRequest);
        logger.debug("Successfully Saved user {} in cognito user pool {} with status : {}", userName, userPoolId, adminCreateUserResult.getUser().getUserStatus());
    }

    @Override
    public void updateUser(final String userName, final List<UserAttributeType> userAttributeTypes) {
        final List<AttributeType> attributeTypes = userAttributeTypes.stream().map(userAttributeType -> getAttribute(userAttributeType.getName(), userAttributeType.getValue())).collect(Collectors.toList());
        final AdminUpdateUserAttributesRequest adminUpdateUserRequest = new AdminUpdateUserAttributesRequest().withUserPoolId(userPoolId).withUsername(userName).withUserAttributes(attributeTypes);
        logger.debug("Attempting to update user {} in cognito user pool {} ", userName, userPoolId);
        aWSCognitoIdentityProviderFactory.getInstance().adminUpdateUserAttributes(adminUpdateUserRequest);
        logger.debug("Successfully Updated user {} in cognito user pool {}", userName, userPoolId);
    }


    @Override
    public void deleteUser(final String userName) {
        final AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest().withUserPoolId(userPoolId).withUsername(userName);
        logger.debug("Attempting to delete user {} in cognito user pool {} ", userName, userPoolId);
        aWSCognitoIdentityProviderFactory.getInstance().adminDeleteUser(adminDeleteUserRequest);
        logger.debug("Successfully deleted user {} from cognito user pool {}", userName, userPoolId);
    }

    private AttributeType getAttribute(final String name, final String value) {
        return new AttributeType().withName(name).withValue(value);
    }
}
