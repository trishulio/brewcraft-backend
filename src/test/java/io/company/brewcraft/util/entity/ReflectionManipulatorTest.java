package io.company.brewcraft.util.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.util.Dummy;

public class ReflectionManipulatorTest {

    private ReflectionManipulator util;

    @BeforeEach
    public void init() {
        util = ReflectionManipulator.INSTANCE;
    }

    @Test
    public void testEquals_ReturnsTrue_WhenBothArgsAreNull() {
        assertTrue(util.equals(null, null));
    }

    @Test
    public void testEquals_ReturnsFalse_WhenArgIsNullAndOtherIsNot() {
        assertFalse(util.equals(new Object(), null));
        assertFalse(util.equals(null, new Object()));
    }

    @Test
    public void testEquals_ReturnsTrue_WhenObjectsAreSame() {
        Object o = new Object();
        assertTrue(util.equals(o, o));
    }

    @Test
    public void testEquals_ReturnsFalse_WhenObjectsAreNotEquals() {
        class Other {
            int x;
        }
        Other a = new Other();
        Other b = new Other();
        a.x = 10;
        b.x = 20;
        assertFalse(util.equals(a, b));
    }

    @Test
    public void testOuterJoin_ThrowsException_WhenEitherObjectIsNull() {
        assertThrows(IllegalArgumentException.class, () -> util.outerJoin(null, null, pd -> true), "Outer Joins can not be on null objects or objects of different classes");
        assertThrows(IllegalArgumentException.class, () -> util.outerJoin(null, new Dummy(), pd -> true), "Outer Joins can not be on null objects or objects of different classes");
        assertThrows(IllegalArgumentException.class, () -> util.outerJoin(new Dummy(), null, pd -> true), "Outer Joins can not be on null objects or objects of different classes");
    }

    @Test
    public void testOuterJoin_ThrowsException_WhenObjectsAreNotOfTheSameClass() {
        class Other {
        }
        assertThrows(IllegalArgumentException.class, () -> util.outerJoin(new Dummy(), new Other(), pd -> true), "Outer Joins can not be on null objects or objects of different classes");
        assertThrows(IllegalArgumentException.class, () -> util.outerJoin(new Other(), new Dummy(), pd -> true), "Outer Joins can not be on null objects or objects of different classes");
    }

    @Test
    public void testOuterJoin_SkipsProperty_WhenGetterIsMissing() {
        class Other {
            int x;

            public Other(int x) {
                setX(x);
            }

            public void setX(int x) {
                this.x = x;
            }
        }

        Other o1 = new Other(10);
        Other o2 = new Other(20);

        util.outerJoin(o1, o2, pd -> true);

        assertEquals(10, o1.x);
        assertEquals(20, o2.x);
    }

    @Test
    public void testOuterJoin_SkipsProperty_WhenSetterIsMissing() {
        class Other {
            int x;

            public Other(int x) {
                this.x = x;
            }

            public int getX() {
                return x;
            }
        }

        Other o1 = new Other(10);
        Other o2 = new Other(20);

        util.outerJoin(o1, o2, pd -> true);

        assertEquals(10, o1.getX());
        assertEquals(20, o2.getX());
    }

    @Test
    public void testOuterJoin_SkipsProperty_WhenPredicateReturnsFalse() {
        class Other {
            int x;

            public Other(int x) {
                setX(x);
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getX() {
                return x;
            }
        }

        Other o1 = new Other(10);
        Other o2 = new Other(20);

        util.outerJoin(o1, o2, pd -> false);

        assertEquals(10, o1.getX());
        assertEquals(20, o2.getX());
    }

    @Test
    public void testOuterJoin_CopiesProperty_WhenGetterAndSetterAreBothPresentAndPredicateReturnsTrue() {
        class Other {
            int x;

            public Other(int x) {
                setX(x);
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getX() {
                return x;
            }
        }

        Other o1 = new Other(10);
        Other o2 = new Other(20);

        util.outerJoin(o1, o2, pd -> true);

        assertEquals(20, o1.getX());
        assertEquals(20, o2.getX());
    }
}
