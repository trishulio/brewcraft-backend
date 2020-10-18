package io.company.brewcraft.data;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantConnectionProviderTest {

    private TenantDataSourceManager tenantDataSourceManagerMock;
    private final String adminIdentifierMock = "master";
    private TenantConnectionProvider tenantConnectionProvider;
    
    @BeforeEach
    public void init() {
        tenantDataSourceManagerMock = mock(TenantDataSourceManager.class);
        
        tenantConnectionProvider = new TenantConnectionProvider(tenantDataSourceManagerMock, adminIdentifierMock);
    }

    @Test
    public void test() {
        //TODO
    }
}
