package io.company.brewcraft.security.idp;

public interface IdentityProvider {

    void createUser(String userName, String email);

    void deleteUser(String userName);
}
