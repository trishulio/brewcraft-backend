package io.company.brewcraft.service.impl.user;

import static org.mockito.Mockito.mock;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.security.idp.AwsCognitoIdpClient;
import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.service.IdpUserRepository;

public class AwsIdpUserRepositoryTest {

    private AwsCognitoIdpClient mClient;

    private IdpUserRepository repo;

    @BeforeEach
    public void init() {
        mClient = mock(AwsCognitoIdpClient.class);
        repo = new AwsIdpUserRepository(mClient, null);
    }

    @Test
    public void testCreateUser_SavesUserWithCognitoAttributes() {
        User user = new User();
        user.setUserName("USERNAME");
        user.setEmail("EMAIL");
        repo.createUser(user);

        mClient.createUser("USERNAME", Map.of(CognitoPrincipalContext.ATTRIBUTE_EMAIL, "EMAIL", CognitoPrincipalContext.ATTRIBUTE_EMAIL_VERIFIED, "true"));
    }

    @Test
    public void testUpdateUser_UpdatesUserWithCognitoAttributes() {
        User user = new User();
        user.setUserName("USERNAME");
        user.setEmail("EMAIL");
        repo.createUser(user);

        mClient.updateUser("USERNAME", Map.of(CognitoPrincipalContext.ATTRIBUTE_EMAIL, "EMAIL"));
    }

    @Test
    public void testDeleteUser_DeletesCognitoAttributes() {
        User user = new User();
        user.setUserName("USERNAME");
        repo.deleteUser("USERNAME");

        mClient.deleteUser("USERNAME");
    }
}
