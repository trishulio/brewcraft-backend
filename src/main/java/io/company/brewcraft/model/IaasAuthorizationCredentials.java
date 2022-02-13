package io.company.brewcraft.model;

public class IaasAuthorizationCredentials extends BaseModel {
    private String token;

    public IaasAuthorizationCredentials(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return this.token;
    }
}
