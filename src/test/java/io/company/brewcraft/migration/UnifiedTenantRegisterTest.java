package io.company.brewcraft.migration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

public class UnifiedTenantRegisterTest {

    private TenantRegister register;

    private TenantUserRegister mUserReg;
    private TenantSchemaRegister mSchemaReg;

    private InOrder order;

    @BeforeEach
    public void init() {
        mUserReg = mock(TenantUserRegister.class);
        mSchemaReg = mock(TenantSchemaRegister.class);

        register = new UnifiedTenantRegister(mUserReg, mSchemaReg);

        order = inOrder(mUserReg, mSchemaReg);
    }

    @Test
    public void testAdd_CallsAddOnAllRegisters() {
        register.add("12345");

        order.verify(mUserReg, times(1)).add("12345");
        order.verify(mSchemaReg, times(1)).add("12345");
    }

    @Test
    public void testRemove_CallsRemoveOnSchemaAndUserRegisters() {
        register.remove("12345");

        order.verify(mSchemaReg, times(1)).remove("12345");
        order.verify(mUserReg, times(1)).remove("12345");
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllRegisterExistsReturnTrue() {
        doReturn(true).when(mUserReg).exists("12345");
        doReturn(true).when(mSchemaReg).exists("12345");

        boolean b = register.exists("12345");
        assertTrue(b);
    }

    @Test
    public void testExists_ReturnsFalse_WhenUserRegisterExistsReturnFalse() {
        doReturn(false).when(mUserReg).exists("12345");
        doReturn(true).when(mSchemaReg).exists("12345");

        boolean b = register.exists("12345");
        assertFalse(b);
    }

    @Test
    public void testExists_ReturnsFalse_WhenSchemaRegisterExistsReturnFalse() {
        doReturn(true).when(mUserReg).exists("12345");
        doReturn(false).when(mSchemaReg).exists("12345");

        boolean b = register.exists("12345");
        assertFalse(b);
    }
}
