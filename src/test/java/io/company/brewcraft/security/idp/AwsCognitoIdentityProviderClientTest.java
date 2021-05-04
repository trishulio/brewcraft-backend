package io.company.brewcraft.security.idp;


import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserResult;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AwsCognitoIdentityProviderClientTest {

    private AwsCognitoIdentityProviderClient awsCognitoIdentityProviderClient;

    private AWSCognitoIdentityProviderFactory awsCognitoIdentityProviderFactory;

    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @BeforeEach
    public void init() {
        final String userPoolId = "testUserPoolId";
        awsCognitoIdentityProviderFactory = mock(AWSCognitoIdentityProviderFactory.class);
        awsCognitoIdentityProvider = mock(AWSCognitoIdentityProvider.class);
        doReturn(awsCognitoIdentityProvider).when(awsCognitoIdentityProviderFactory).getInstance();
        awsCognitoIdentityProviderClient = new AwsCognitoIdentityProviderClient(awsCognitoIdentityProviderFactory, userPoolId);
    }

    @Test
    public void testCreateUser_CreatesCognitoUser_WhenUserNameAndAttributesProvided() {
        final String userName = "testUserName";
        final UserAttributeType userAttributeForEmail = getUserAttributeTypeForEmail();
        ArgumentCaptor<AdminCreateUserRequest> cognitoCreateUserRequestCaptor = ArgumentCaptor.forClass(AdminCreateUserRequest.class);
        final AdminCreateUserResult adminCreateUserResult = getAdminCreateUserResult();
        when(awsCognitoIdentityProvider.adminCreateUser(cognitoCreateUserRequestCaptor.capture())).thenReturn(adminCreateUserResult);

        awsCognitoIdentityProviderClient.createUser(userName, Collections.singletonList(userAttributeForEmail));

        final AdminCreateUserRequest adminCreateUserRequest = cognitoCreateUserRequestCaptor.getValue();
        assertEquals(userName, adminCreateUserRequest.getUsername());
        final Optional<AttributeType> congnitoUserAttributeForEmail = getAttributeByName(userAttributeForEmail.getName(), adminCreateUserRequest.getUserAttributes());
        assertTrue(congnitoUserAttributeForEmail.isPresent());
        assertEquals(userAttributeForEmail.getValue(), congnitoUserAttributeForEmail.get().getValue());
    }

    @Test
    public void testUpdateUser_UpdatesCognitoUserAttributes_WhenUserNameAndAttributesProvided() {
        final String userName = "testUserName";
        final UserAttributeType userAttributeForEmail = getUserAttributeTypeForEmail();
        ArgumentCaptor<AdminUpdateUserAttributesRequest> cognitoUpdateUserAttributesRequestCaptor = ArgumentCaptor.forClass(AdminUpdateUserAttributesRequest.class);
        final AdminUpdateUserAttributesResult adminUpdateUserAttributesResult = mock(AdminUpdateUserAttributesResult.class);
        when(awsCognitoIdentityProvider.adminUpdateUserAttributes(cognitoUpdateUserAttributesRequestCaptor.capture())).thenReturn(adminUpdateUserAttributesResult);

        awsCognitoIdentityProviderClient.updateUser(userName, Collections.singletonList(userAttributeForEmail));

        final AdminUpdateUserAttributesRequest adminUpdateUserAttributesRequest = cognitoUpdateUserAttributesRequestCaptor.getValue();
        assertEquals(userName, adminUpdateUserAttributesRequest.getUsername());
        final Optional<AttributeType> congnitoUserAttributeForEmail = getAttributeByName(userAttributeForEmail.getName(), adminUpdateUserAttributesRequest.getUserAttributes());
        assertTrue(congnitoUserAttributeForEmail.isPresent());
        assertEquals(userAttributeForEmail.getValue(), congnitoUserAttributeForEmail.get().getValue());
    }

    @Test
    public void testDeleteUser_DeletesCognitoUser_WhenUserNameIsProvided() {
        final String userName = "testUserName";
        ArgumentCaptor<AdminDeleteUserRequest> cognitoDeleteUserRequestCaptor = ArgumentCaptor.forClass(AdminDeleteUserRequest.class);
        final AdminDeleteUserResult adminDeleteUserResult = mock(AdminDeleteUserResult.class);
        when(awsCognitoIdentityProvider.adminDeleteUser(cognitoDeleteUserRequestCaptor.capture())).thenReturn(adminDeleteUserResult);

        awsCognitoIdentityProviderClient.deleteUser(userName);

        final AdminDeleteUserRequest adminDeleteUserRequest = cognitoDeleteUserRequestCaptor.getValue();
        assertEquals(userName, adminDeleteUserRequest.getUsername());
    }

    private AdminCreateUserResult getAdminCreateUserResult() {
        final AdminCreateUserResult adminCreateUserResult = mock(AdminCreateUserResult.class);
        final UserType userType = mock(UserType.class);
        when(userType.getUserStatus()).thenReturn("enabled");
        when(adminCreateUserResult.getUser()).thenReturn(userType);
        return adminCreateUserResult;
    }

    private Optional<AttributeType> getAttributeByName(final String attributeName, final List<AttributeType> attributes) {
        return attributes.stream().filter(attribute -> attributeName.equals(attribute.getName())).findAny();
    }

    private UserAttributeType getUserAttributeTypeForEmail() {
        final String attributeNameForEmail = "testEmailAttributeName";
        final String attributeValueForEmail = "testEmailAttributeValue";
        return new UserAttributeType(attributeNameForEmail, attributeValueForEmail);
    }
}
