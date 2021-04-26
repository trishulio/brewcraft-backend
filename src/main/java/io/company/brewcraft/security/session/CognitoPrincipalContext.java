package io.company.brewcraft.security.session;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;

public class CognitoPrincipalContext implements PrincipalContext {
    public static final String CLAIM_GROUPS = "cognito:groups";
    public static final String CLAIM_USERNAME = "username";
    public static final String CLAIM_SCOPE = "scope";
    public static final String ATTRIBUTE_EMAIL = "email";
    public static final String ATTRIBUTE_EMAIL_VERIFIED = "email_verified";

    private Jwt jwt;

    public CognitoPrincipalContext(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    public String getTenantId() {
        List<String> groups = this.jwt.getClaimAsStringList(CLAIM_GROUPS);
        if (groups.size() > 1) {
            String msg = String.format("Each user should only belong to a single cognito group. Instead found %s", groups.size());
            throw new IllegalStateException(msg);
        }

        return groups.get(0);
    }

    @Override
    public String getUsername() {
        return this.jwt.getClaimAsString(CLAIM_USERNAME);
    }

    @Override
    public List<String> getRoles() {
        return Arrays.asList(this.jwt.getClaimAsString(CLAIM_SCOPE).split(" "));
    }

}
