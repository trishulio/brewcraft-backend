package io.company.brewcraft.security.idp;

import java.util.Map;

public interface IdentityProviderClient {

    void createUser(String userName, final Map<String, String> userAttr);

    void updateUser(String userName, final Map<String, String> userAttr);

    void deleteUser(String userName);

    void addUserToGroup(String userName, String group);

    void removeUserFromGroup(String userName, String group);

    void createUserGroup(String group);

    void deleteUserGroup(String group);

    boolean groupExists(String group);
}
