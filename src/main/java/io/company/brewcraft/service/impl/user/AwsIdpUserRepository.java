package io.company.brewcraft.service.impl.user;

import java.util.HashMap;
import java.util.Map;

import io.company.brewcraft.model.user.User;
import io.company.brewcraft.security.idp.AwsCognitoIdpClient;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.service.IdpUserRepository;

public class AwsIdpUserRepository implements IdpUserRepository {

    private IdentityProviderClient idpClient;

    public AwsIdpUserRepository(AwsCognitoIdpClient idpClient) {
        this.idpClient = idpClient;
    }

    @Override
    public void createUser(User user) {
        Map<String, String> userAttrs = new HashMap<>();
        userAttrs.put(CognitoPrincipalContext.ATTRIBUTE_EMAIL, user.getEmail());
        userAttrs.put(CognitoPrincipalContext.ATTRIBUTE_EMAIL_VERIFIED, "true"); // TODO: Is this correct?

        this.idpClient.createUser(user.getUserName(), userAttrs);
    }

    @Override
    public void updateUser(User user) {
        Map<String, String> userAttrs = new HashMap<>();
        userAttrs.put(CognitoPrincipalContext.ATTRIBUTE_EMAIL, user.getEmail());
        // TODO: Optimize for skipping the idpClient call when attributes aren't changed for the user.

        this.idpClient.updateUser(user.getUserName(), userAttrs);
    }

    @Override
    public void deleteUser(User user) {
        this.idpClient.deleteUser(user.getUserName());
    }
}
