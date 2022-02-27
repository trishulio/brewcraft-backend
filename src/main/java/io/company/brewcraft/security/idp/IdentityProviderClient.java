package io.company.brewcraft.security.idp;

import java.util.Map;

public interface IdentityProviderClient {

    void createUser(String userName, final Map<String, String> userAttr);

    void updateUser(String userName, final Map<String, String> userAttr);

    void deleteUser(String userName);

    void addUserToGroup(String userName, String group);

    void removeUserFromGroup(String userName, String group);

    void createGroup(String group, String roleArn);

    void updateGroup(String group, String roleArn);
    
    void deleteGroup(String group);

    boolean groupExists(String group);
}
