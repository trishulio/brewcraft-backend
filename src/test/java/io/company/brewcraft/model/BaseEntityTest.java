package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.company.brewcraft.data.CheckedBiFunction;
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
    public void testOuterJoin_CallsUtilOuterJoinWithTrueReturningPredicate_WhenGetterReturnsNull() throws ReflectiveOperationException {
        ArgumentCaptor<CheckedBiFunction<Boolean, Method, Method, ReflectiveOperationException>> captor = ArgumentCaptor.forClass(CheckedBiFunction.class);
        doNothing().when(util).outerJoin(eq(entity), eq(other), captor.capture());
        entity.outerJoin(other);

        Method idGetter = TestBaseEntity.class.getDeclaredMethod("getX");
        entity.setX(null);
        assertTrue(captor.getValue().apply(idGetter, null));
    }

    @Test
    public void testOuterJoin_CallsUtilOuterJoinWithFalseReturningPredicate_WhenGetterReturnsNonNull() throws ReflectiveOperationException {
        ArgumentCaptor<CheckedBiFunction<Boolean, Method, Method, ReflectiveOperationException>> captor = ArgumentCaptor.forClass(CheckedBiFunction.class);
        doNothing().when(util).outerJoin(eq(entity), eq(other), captor.capture());
        entity.outerJoin(other);

        Method idGetter = TestBaseEntity.class.getDeclaredMethod("getX");
        entity.setX(12345);
        assertFalse(captor.getValue().apply(idGetter, null));
    }
}
