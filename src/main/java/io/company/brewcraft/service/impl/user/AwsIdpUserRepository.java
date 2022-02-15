package io.company.brewcraft.service.impl.user;

import java.util.HashMap;
import java.util.Map;

import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.security.idp.AwsCognitoIdpClient;
import io.company.brewcraft.security.idp.IdentityProviderClient;
import io.company.brewcraft.security.session.CognitoPrincipalContext;
import io.company.brewcraft.service.IdpUserRepository;
import io.company.brewcraft.service.user.Group;

public class AwsIdpUserRepository implements IdpUserRepository {

    private IdentityProviderClient idpClient;

    public AwsIdpUserRepository(AwsCognitoIdpClient idpClient) {
        this.idpClient = idpClient;
    }

    @Override
    public void createUser(User user) {
        Map<String, String> userAttrs = new HashMap<>();
        userAttrs.put(CognitoPrincipalContext.ATTRIBUTE_EMAIL, user.getEmail());
        userAttrs.put(CognitoPrincipalContext.ATTRIBUTE_EMAIL_VERIFIED, "true");

        this.idpClient.createUser(user.getEmail(), userAttrs);
    }

    @Override
    public void updateUser(User user) {
        Map<String, String> userAttrs = new HashMap<>();
        userAttrs.put(CognitoPrincipalContext.ATTRIBUTE_EMAIL, user.getEmail());
        // TODO: Optimize for skipping the idpClient call when attributes aren't changed for the user.

        this.idpClient.updateUser(user.getEmail(), userAttrs);
    }

    @Override
    public void deleteUser(User user) {
        this.idpClient.deleteUser(user.getEmail());
    }

    @Override
    public void addUserToGroup(User user, String group) {
        this.idpClient.addUserToGroup(user.getEmail(), group);
    }

    @Override
    public void removeUserFromGroup(User user, String group) {
        this.idpClient.removeUserFromGroup(user.getEmail(), group);
    }

    @Override
    public void createUserGroup(Group group) {
        String roleArn = null;
        IaasRole role = group.getIaasRole();

        if (role != null) {
            roleArn = role.getIaasResourceName();
        }

        this.idpClient.createUserGroup(group.getId(), roleArn);
    }

    @Override
    public void deleteUserGroup(String group) {
        this.idpClient.deleteUserGroup(group);
    }

    @Override
    public boolean userGroupExists(String group) {
        return this.idpClient.userGroupExists(group);
    }

    @Override
    public void putUserGroup(Group group) {
        String groupId = group.getId();
        if (!this.userGroupExists(groupId)) {
            this.createUserGroup(group);
        }
    }

    @Override
    public void createUserInGroup(User user, String group) {
        this.createUser(user);

        try {
            this.addUserToGroup(user, group);
        } catch (Exception e) {
            this.deleteUser(user);
            throw e;
        }
    }
}
