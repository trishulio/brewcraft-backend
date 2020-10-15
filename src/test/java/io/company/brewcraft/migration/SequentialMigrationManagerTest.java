package io.company.brewcraft.migration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
public class SequentialMigrationManagerTest {

    private MigrationManager mgr;

    private TenantRegister mReg;

    @BeforeEach
    public void init() {
        mReg = mock(TenantRegister.class);
        mgr = new SequentialMigrationManager(mReg);
    }

    @Test
    public void testMigrate_CallsAddOnRegister_WhenTenantDoesNotExists() {
        doReturn(false).when(mReg).exists("12345");

        mgr.migrate("12345");
        verify(mReg, times(1)).add("12345");
    }

    @Test
    public void testMigrate_ThrowsError_WhenTenantAlreadyExists() {
        doReturn(true).when(mReg).exists("12345");
        assertThrows(RuntimeException.class, () -> mgr.migrate("12345"));
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
        doReturn(false).when(mReg).exists(any(String.class));
        doNothing().when(mReg).setup();
        doNothing().when(mReg).add(startsWith("SUCCESS_"));
        doThrow(new RuntimeException()).when(mReg).add(startsWith("FAIL_"));

        TaskSet tasks = new SequentialTaskSet();
        sqMgr.migrateAll(tasks, Arrays.asList("SUCCESS_TENANT_1", "FAIL_TENANT_1", "SUCCESS_TENANT_2", "FAIL_TENANT_2"));

        assertEquals(2, tasks.getErrors().size());
        assertEquals(3, tasks.getResults().size()); // Also contains setup()

        verify(mReg, times(1)).setup();
    }
}
