package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.company.brewcraft.data.CheckedFunction;
import io.company.brewcraft.util.entity.ReflectionManipulator;

public class BaseEntityTest {
    class TestBaseEntity extends BaseEntity {
        private Integer x;
        public TestBaseEntity(Integer x, ReflectionManipulator util) {
            super(util);
            setX(x);
        }

        public Integer getX() {
            return this.x;
        }

        public void setX(Integer x) {
            this.x = x;
        }
    }

    private TestBaseEntity entity;
    private TestBaseEntity other;
    private ReflectionManipulator util;

    @BeforeEach
    public void init() {
        util = mock(ReflectionManipulator.class);
        entity = new TestBaseEntity(0, util);
        other = new TestBaseEntity(12345, util);
    }

    @Test
    public void testEquals_ReturnsTrue_WhenUtilReturnsTrue() {
        doReturn(true).when(util).equals(entity, other);
        assertTrue(entity.equals(other));
    }

    @Test
    public void testEquals_ReturnsFalse_WhenUtilReturnsFalse() {
        doReturn(false).when(util).equals(entity, other);
        assertFalse(entity.equals(other));
    }

    @Test
    public void testOuterJoin_CallsUtilOuterJoinWithPredicate_ThatReturnsFalseWhenGetterReturnsNull() throws ReflectiveOperationException {
        ArgumentCaptor<CheckedFunction<Boolean, PropertyDescriptor, ReflectiveOperationException>> captor = ArgumentCaptor.forClass(CheckedFunction.class);
        doNothing().when(util).copy(eq(entity), eq(other), captor.capture());
        entity.outerJoin(other);

        Method idGetter = TestBaseEntity.class.getDeclaredMethod("getX");
        PropertyDescriptor mPd = mock(PropertyDescriptor.class);
        doReturn(idGetter).when(mPd).getReadMethod();
        other.setX(null);
        assertFalse(captor.getValue().apply(mPd));
    }

    @Test
    public void testOuterJoin_CallsUtilOuterJoinWithPredicate_ThatReturnsTrueWhenGetterReturnsNonNull() throws ReflectiveOperationException {
        ArgumentCaptor<CheckedFunction<Boolean, PropertyDescriptor, ReflectiveOperationException>> captor = ArgumentCaptor.forClass(CheckedFunction.class);
        doNothing().when(util).copy(eq(entity), eq(other), captor.capture());
        entity.outerJoin(other);

        Method idGetter = TestBaseEntity.class.getDeclaredMethod("getX");
        PropertyDescriptor mPd = mock(PropertyDescriptor.class);
        doReturn(idGetter).when(mPd).getReadMethod();
        other.setX(12345);
        assertTrue(captor.getValue().apply(mPd));
    }
}
