package io.company.brewcraft.security.idp;

import java.util.Map;

public interface IdentityProviderClient {

    void createUser(String userName, final Map<String, String> userAttr);

    void updateUser(String userName, final Map<String, String> userAttr);

    void deleteUser(String userName);
}
