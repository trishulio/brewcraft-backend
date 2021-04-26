package io.company.brewcraft.security.idp;

import java.util.List;

public interface IdentityProviderClient {

    void createUser(String userName, List<UserAttributeType> userAttributeTypeList);

    void updateUser(String userName, List<UserAttributeType> userAttributeTypeList);

    void deleteUser(String userName);
}
