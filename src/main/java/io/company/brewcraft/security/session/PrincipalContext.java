package io.company.brewcraft.security.session;

import java.util.List;

public interface PrincipalContext {

    String getTenantId();

    String getUsername();

    List<String> getRoles();
}
