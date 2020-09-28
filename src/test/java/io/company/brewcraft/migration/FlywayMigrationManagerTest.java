package io.company.brewcraft.migration;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlywayMigrationManagerTest {

    private MigrationManager mgr;

    private TenantRegister mRegister;

    @BeforeEach
    public void init() {
        mRegister = mock(TenantRegister.class);
        mgr = new FlywayMigrationManager(mRegister);
    }

    @Test
    public void testMigrate_CreatesUserAndRegisterTenant_WhenUserDoesntExist() {
        doReturn(false).when(mRegister).userExists("12345");

        mgr.migrate("12345");

        verify(mRegister, times(1)).registerUser("12345");
        verify(mRegister, times(1)).registerTenant("12345");
    }

    @Test
    public void testMigrate_OnlyRegisterTenantAndNotCreateUser_WhenUserExists() {
        doReturn(true).when(mRegister).userExists("12345");

        mgr.migrate("12345");

        verify(mRegister, times(0)).registerUser("12345");
        verify(mRegister, times(1)).registerTenant("12345");
    }

    @Test
    public void testMigrateAll_RegistersAppAndRegistersAllTenants() {
        doReturn(false).when(mRegister).userExists(anyString());

        mgr.migrateAll();

        verify(mRegister, times(1)).registerApp();

        verify(mRegister, times(1)).registerUser("eae07f11_4c9a_4a3b_8b23_9c05d695ab67");
        verify(mRegister, times(1)).registerTenant("eae07f11_4c9a_4a3b_8b23_9c05d695ab67");

        verify(mRegister, times(1)).registerUser("eae07f11_4c9a_4a3b_8b23_9c05d695ab68");
        verify(mRegister, times(1)).registerUser("eae07f11_4c9a_4a3b_8b23_9c05d695ab68");

    }
}
