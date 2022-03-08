package io.company.brewcraft.security.idp;

import java.util.Map;

import com.amazonaws.services.cognitoidp.model.GroupType;
import com.amazonaws.services.cognitoidp.model.UserType;

public interface IdentityProviderClient {

    UserType createUser(String userName, final Map<String, String> userAttr);

    void updateUser(String userName, final Map<String, String> userAttr);

    void deleteUser(String userName);

    void addUserToGroup(String userName, String group);

    void removeUserFromGroup(String userName, String group);

    GroupType getGroup(String group);
    
    GroupType createGroup(String group, String roleArn);
    
    GroupType putGroup(String group, String roleArn);

    GroupType updateGroup(String group, String roleArn);
    
    void deleteGroup(String group);

    boolean groupExists(String group);
}
