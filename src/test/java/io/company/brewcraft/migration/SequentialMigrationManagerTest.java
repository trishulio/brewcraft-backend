package io.company.brewcraft.migration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.IdpUserRepository;

@SuppressWarnings("unchecked")
public class SequentialMigrationManagerTest {

    private MigrationManager mgr;

    private TenantRegister mTenantReg;
    private MigrationRegister mMigrationReg;
    private IdpUserRepository mIdpUserRepo;

    @BeforeEach
    public void init() {
        mTenantReg = mock(TenantRegister.class);
        mMigrationReg = mock(MigrationRegister.class);
        mIdpUserRepo = mock(IdpUserRepository.class);

        mgr = new SequentialMigrationManager(mTenantReg, mMigrationReg, mIdpUserRepo);
    }

    @Test
    public void testMigrate_DoesNotAddTenantAndRunsMigration_WhenTenantExist() {
        doReturn(true).when(mTenantReg).exists("12345");
        mgr.migrate("12345");

        verify(mTenantReg, times(0)).add("12345");
        verify(mMigrationReg, times(1)).migrate("12345");
        verify(mIdpUserRepo, times(1)).putUserGroup("12345");
    }

    @Test
    public void testMigrate_AddsTenantAndCreatesGroupAndRunsMigration_WhenTenantDoesNotExist() {
        doReturn(false).when(mTenantReg).exists("12345");
        mgr.migrate("12345");

        verify(mTenantReg, times(1)).add("12345");
        verify(mMigrationReg, times(1)).migrate("12345");
        verify(mIdpUserRepo, times(1)).putUserGroup("12345");
    }

    @Test
    public void migrateAll_CallsOverloadedMigrateMethod_WithNewSequentialTaskSetAndTestTenantList() {
        SequentialMigrationManager sqMgr = spy((SequentialMigrationManager) mgr);
        doNothing().when(sqMgr).migrateAll(any(TaskSet.class), any(List.class));

        sqMgr.migrateAll();

        verify(sqMgr, times(1)).migrateAll(any(TaskSet.class), any(List.class));
    }

    @Test
    public void migrateAllImpl_SubmitsSetupAndAllMigrationTasksToTaskSet() {
        SequentialMigrationManager sqMgr = spy((SequentialMigrationManager) mgr);
        doReturn(false).when(mTenantReg).exists(any(String.class));
        doReturn(false).when(mIdpUserRepo).userGroupExists(any(String.class));
        doNothing().when(mMigrationReg).migrate();
        doNothing().when(mTenantReg).add(startsWith("SUCCESS_"));
        doNothing().when(mIdpUserRepo).putUserGroup(startsWith("SUCCESS_"));
        doThrow(new RuntimeException()).when(mTenantReg).add(startsWith("FAIL_"));

        TaskSet tasks = new SequentialTaskSet();
        sqMgr.migrateAll(tasks, Arrays.asList("SUCCESS_TENANT_1", "FAIL_TENANT_1", "SUCCESS_TENANT_2", "FAIL_TENANT_2"));

        assertEquals(2, tasks.getErrors().size());
        assertEquals(3, tasks.getResults().size()); // Also contains setup()

        verify(mMigrationReg, times(1)).migrate();
    }
}
