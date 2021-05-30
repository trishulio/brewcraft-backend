package io.company.brewcraft.security.idp;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UserType;

public class AwsCognitoIdpClientTest {

    private AWSCognitoIdentityProvider mAwsIdp;

    private AwsCognitoIdpClient client;

    @BeforeEach
    public void init() {
        mAwsIdp = mock(AWSCognitoIdentityProvider.class);
        client = new AwsCognitoIdpClient(mAwsIdp, "USER_POOL");
    }

    @Test
    public void testCreateUser_CallsAwsIdpWithCreateRequest() {
        ArgumentCaptor<AdminCreateUserRequest> captor = ArgumentCaptor.forClass(AdminCreateUserRequest.class);

        AdminCreateUserResult mRes = new AdminCreateUserResult().withUser(new UserType().withUserStatus("SUCCESS"));
        doReturn(mRes).when(mAwsIdp).adminCreateUser(captor.capture());
        
        client.createUser("USERNAME", Map.of("key-1", "value-1", "key-2", "value-2"));
        
        List<AttributeType> expected = List.of(
            new AttributeType().withName("key-2").withValue("value-2"),
            new AttributeType().withName("key-1").withValue("value-1")
        );
        assertEquals(expected, captor.getValue().getUserAttributes());
        assertEquals("USERNAME", captor.getValue().getUsername());
        assertEquals("USER_POOL", captor.getValue().getUserPoolId());
    }

    @Test
    public void testUpdateUser_CallsAwsIdpWithUpdateRequest() {
        ArgumentCaptor<AdminUpdateUserAttributesRequest> captor = ArgumentCaptor.forClass(AdminUpdateUserAttributesRequest.class);
        doReturn(null).when(mAwsIdp).adminUpdateUserAttributes(captor.capture());

        client.updateUser("USERNAME", Map.of("key-1", "value-1", "key-2", "value-2"));

        List<AttributeType> expected = List.of(
            new AttributeType().withName("key-2").withValue("value-2"),
            new AttributeType().withName("key-1").withValue("value-1")
        );
        assertEquals(expected, captor.getValue().getUserAttributes());
        assertEquals("USERNAME", captor.getValue().getUsername());
        assertEquals("USER_POOL", captor.getValue().getUserPoolId());
    }

    @Test
    public void testDeleteUser_CallsAwsIdpWithDeleteRequest() {
        ArgumentCaptor<AdminDeleteUserRequest> captor = ArgumentCaptor.forClass(AdminDeleteUserRequest.class);
        doReturn(null).when(mAwsIdp).adminDeleteUser(captor.capture());

        client.deleteUser("USERNAME");

        assertEquals("USERNAME", captor.getValue().getUsername());
        assertEquals("USER_POOL", captor.getValue().getUserPoolId());
    }
}
