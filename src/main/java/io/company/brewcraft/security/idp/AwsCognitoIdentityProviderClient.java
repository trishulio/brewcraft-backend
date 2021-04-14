package io.company.brewcraft.security.idp;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.MessageActionType;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class AwsCognitoIdentityProviderClient implements IdentityProvider {

    private AWSCognitoIdentityProvider identityProviderClient;

    private final String cognitoRegion;

    private final String cognitoUrl;

    private final String cognitoAccessKey;

    private final String cognitoSecretKey;

    private final AwsCognitoIdentityProviderFactory awsCognitoIdentityProviderFactory;

    private final String userPoolId;

    private static final String EMAIL_ATTRIBUTE = "email";

    private static final String EMAIL_VERIFIED_ATTRIBUTE = "email_verified";

    public AwsCognitoIdentityProviderClient(@Value("${aws.cognito.region}") final String cognitoRegion, @Value("${aws.cognito.url}") final String cognitoUrl, @Value("${aws.cognito.accessKey}") final String cognitoAccessKey, @Value("${aws.cognito.secretKey}") final String cognitoSecretKey, @Value("${aws.cognito.userPoolId}") final String userPoolId, final AwsCognitoIdentityProviderFactory awsCognitoIdentityProviderFactory) {
        this.cognitoRegion = cognitoRegion;
        this.cognitoUrl = cognitoUrl;
        this.cognitoAccessKey = cognitoAccessKey;
        this.cognitoSecretKey = cognitoSecretKey;
        this.userPoolId = userPoolId;
        this.awsCognitoIdentityProviderFactory = awsCognitoIdentityProviderFactory;
    }

    @PostConstruct
    public void initializeAwsCognitoIdentityProvider() {
        identityProviderClient = awsCognitoIdentityProviderFactory.getIdentityProviderClient(cognitoRegion, cognitoUrl, cognitoAccessKey, cognitoSecretKey);
    }

    @Override
    public void createUser(final String userName, final String email) {
        final AttributeType attributeEmail = getAttribute(EMAIL_ATTRIBUTE, email);
        final AttributeType attributeEmailVerified = getAttribute(EMAIL_VERIFIED_ATTRIBUTE, "true");
        final AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest().withUserPoolId(userPoolId).withUsername(userName).withMessageAction(MessageActionType.SUPPRESS).withUserAttributes(attributeEmail, attributeEmailVerified);
        identityProviderClient.adminCreateUser(adminCreateUserRequest);
    }

    @Override
    public void deleteUser(final String userName) {
        final AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest().withUserPoolId(userPoolId).withUsername(userName);
        identityProviderClient.adminDeleteUser(adminDeleteUserRequest);
    }

    private AttributeType getAttribute(final String name, final String value) {
        return new AttributeType().withName(name).withValue(value);
    }

}
