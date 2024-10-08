package io.company.brewcraft.security.session;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.IaasAuthorizationCredentials;

public class CognitoPrincipalContext extends BaseModel implements PrincipalContext {
    public static final String CLAIM_GROUPS = "cognito:groups";
    public static final String CLAIM_USERNAME = "username";
    public static final String CLAIM_SCOPE = "scope";
    public static final String ATTRIBUTE_EMAIL = "email";
    public static final String ATTRIBUTE_EMAIL_VERIFIED = "email_verified";

    private UUID tenantId;
    private String username;
    private List<String> roles;

    private IaasAuthorizationCredentials iaasToken;

    public CognitoPrincipalContext(Jwt jwt, String iaasToken) {
        if (jwt != null) {
            setUsername(jwt);
            setTenantId(jwt);
            setRoles(jwt);
        }

        if (iaasToken != null) {
            setIaasToken(iaasToken);
        }
    }

    @Override
    public UUID getTenantId() {
        return this.tenantId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public List<String> getRoles() {
        return this.roles;
    }

    @Override
    public IaasAuthorizationCredentials getIaasLogin() {
        return this.iaasToken;
    }

    private void setUsername(Jwt jwt) {
        this.username = jwt.getClaimAsString(CLAIM_USERNAME);
    }

    private void setRoles(Jwt jwt) {
        this.roles = Arrays.asList(jwt.getClaimAsString(CLAIM_SCOPE).split(" "));
    }

    private void setTenantId(Jwt jwt) {
        List<String> groups = jwt.getClaimAsStringList(CLAIM_GROUPS);
        if (groups.size() > 1) {
            String msg = String.format("Each user should only belong to a single cognito group. Instead found %s", groups.size());
            throw new IllegalArgumentException(msg);
        }

        String sTenantId = groups.get(0);

        UUID tenantId = UUID.fromString(sTenantId);

        this.tenantId = tenantId;

    }

    private void setIaasToken(String iaasToken) {
        this.iaasToken = new IaasAuthorizationCredentials(iaasToken);
    }
}
