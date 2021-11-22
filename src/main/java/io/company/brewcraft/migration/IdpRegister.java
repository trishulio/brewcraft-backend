package io.company.brewcraft.migration;

public interface IdpRegister {

    void createGroup(String group);

    void deleteGroup(String group);

    boolean groupExists(String group);
}
