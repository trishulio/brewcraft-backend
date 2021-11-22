package io.company.brewcraft.migration;

import io.company.brewcraft.service.IdpUserRepository;

public class CognitoIdpRegister implements IdpRegister {

    private IdpUserRepository idpUserRepository;

    public CognitoIdpRegister(IdpUserRepository idpUserRepository) {
        this.idpUserRepository = idpUserRepository;
    }

    @Override
    public void createGroup(String group) {
        idpUserRepository.createUserGroup(group);
    }

    @Override
    public void deleteGroup(String group) {
        idpUserRepository.deleteUserGroup(group);
    }

    @Override
    public boolean groupExists(String group) {
        return idpUserRepository.groupExists(group);
    }

}
