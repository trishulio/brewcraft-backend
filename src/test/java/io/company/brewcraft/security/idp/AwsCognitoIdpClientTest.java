package io.company.brewcraft.security.idp;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserResult;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UserType;

public class AwsCognitoIdpClientTest {

    private AwsCognitoIdpClient awsCognitoIdpClient;
    private AWSCognitoIdentityProvider awsCognitoIdp;

    @BeforeEach
    public void init() {
        final String userPoolId = "testUserPoolId";
        awsCognitoIdp = mock(AWSCognitoIdentityProvider.class);
        awsCognitoIdpClient = new AwsCognitoIdpClient(awsCognitoIdp, userPoolId);
    }

    @Test
    public void testCreateUser_CreatesCognitoUser_WhenUserNameAndAttributesProvided() {
        final String userName = "testUserName";
        final UserAttributeType userAttributeForEmail = getUserAttributeTypeForEmail();
        ArgumentCaptor<AdminCreateUserRequest> cognitoCreateUserRequestCaptor = ArgumentCaptor.forClass(AdminCreateUserRequest.class);
        final AdminCreateUserResult adminCreateUserResult = getAdminCreateUserResult();
        when(awsCognitoIdp.adminCreateUser(cognitoCreateUserRequestCaptor.capture())).thenReturn(adminCreateUserResult);

        awsCognitoIdpClient.createUser(userName, Collections.singletonList(userAttributeForEmail));

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
        when(awsCognitoIdp.adminUpdateUserAttributes(cognitoUpdateUserAttributesRequestCaptor.capture())).thenReturn(adminUpdateUserAttributesResult);

        awsCognitoIdpClient.updateUser(userName, Collections.singletonList(userAttributeForEmail));

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
        when(awsCognitoIdp.adminDeleteUser(cognitoDeleteUserRequestCaptor.capture())).thenReturn(adminDeleteUserResult);

        awsCognitoIdpClient.deleteUser(userName);

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
