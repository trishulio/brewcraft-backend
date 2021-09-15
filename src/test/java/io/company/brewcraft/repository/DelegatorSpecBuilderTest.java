package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings({ "unchecked" })
public class DelegatorSpecBuilderTest {

    private SpecificationBuilder builder;

    private CriteriaSpecBuilder mDelegate;

    @BeforeEach
    public void init() {
        mDelegate = mock(CriteriaSpecBuilder.class);
        builder = new DelegatorSpecBuilder(mDelegate);
    }

    @Test
    public void testBuilder_ReturnsANewInstanceOfCriteriaSpecBuilder() {
        SpecificationBuilder anotherBuilder = SpecificationBuilder.builder();

        assertNotSame(builder, anotherBuilder);
        assertTrue(builder instanceof DelegatorSpecBuilder, String.format("SpecificationBuilder.builder() unexpectedly returned an instance of class: %s", builder.getClass().getSimpleName()));
    }

    @Test
    public void testIsNull_StringStringArgs_DelegatesArgumentsByWrappingInArrays() {
        SpecificationBuilder ret = builder.isNull("JOIN", "PATH");

        assertSame(builder, ret);
        verify(mDelegate, times(1)).isNull(new String[] { "JOIN" }, new String[] { "PATH" });
    }

    @Test
    public void testIsNull_StringArrayArg_DelegatesArgumentsByWrappingInArrays() {
        SpecificationBuilder ret = builder.isNull("JOIN", new String[] { "PATH" });

        assertSame(builder, ret);
        verify(mDelegate, times(1)).isNull(new String[] { "JOIN" }, new String[] { "PATH" });
    }

    @Test
    public void testIsNull_ArrayStringArgs_DelegatesArgumentsByWrappingInArrays() {
        SpecificationBuilder ret = builder.isNull(new String[] { "JOIN" }, "PATH");

        assertSame(builder, ret);
        verify(mDelegate, times(1)).isNull(new String[] { "JOIN" }, new String[] { "PATH" });
    }

    @Test
    public void testIsNull_ArrayArrayArgs_DelegatesArguments() {
        SpecificationBuilder ret = builder.isNull(new String[] { "JOIN" }, new String[] { "PATH" });

        assertSame(builder, ret);
        verify(mDelegate, times(1)).isNull(new String[] { "JOIN" }, new String[] { "PATH" });
    }

    @Test
    public void testIsNull_StringArgs_DelegatesArgumentsByWrappingInArraysAndNullForMissing() {
        SpecificationBuilder ret = builder.isNull("PATH");

        assertSame(builder, ret);
        verify(mDelegate, times(1)).isNull(null, new String[] { "PATH" });
    }

    @Test
    public void testIsNull_ArrayArgs_DelegatesArgumentsByWrappingInArraysAndNullForMissing() {
        SpecificationBuilder ret = builder.isNull(new String[] { "PATH" });

        assertSame(builder, ret);
        verify(mDelegate, times(1)).isNull(null, new String[] { "PATH" });
    }

    @Test
    public void testIn_StringStringArgs_DelegatesArgumentsByWrappingInArrays() {
        SpecificationBuilder ret = builder.in("JOIN", "PATH", List.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).in(new String[] { "JOIN" }, new String[] { "PATH" }, List.of("v1"));
    }

    @Test
    public void testIn_StringArrayArg_DelegatesArgumentsByWrappingInArrays() {
        SpecificationBuilder ret = builder.in("JOIN", new String[] { "PATH" }, List.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).in(new String[] { "JOIN" }, new String[] { "PATH" }, List.of("v1"));
    }

    @Test
    public void testIn_ArrayStringArgs_DelegatesArgumentsByWrappingInArrays() {
        SpecificationBuilder ret = builder.in(new String[] { "JOIN" }, "PATH", List.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).in(new String[] { "JOIN" }, new String[] { "PATH" }, List.of("v1"));
    }

    @Test
    public void testIn_ArrayArrayArgs_DelegatesArguments() {
        SpecificationBuilder ret = builder.in(new String[] { "JOIN" }, new String[] { "PATH" }, List.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).in(new String[] { "JOIN" }, new String[] { "PATH" }, List.of("v1"));
    }

    @Test
    public void testIn_StringArgs_DelegatesArgumentsByWrappingInArraysAndNullForMissing() {
        SpecificationBuilder ret = builder.in("PATH", List.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).in(null, new String[] { "PATH" }, List.of("v1"));
    }

    @Test
    public void testIn_ArrayArgs_DelegatesArgumentsByWrappingInArraysAndNullForMissing() {
        SpecificationBuilder ret = builder.in(new String[] { "PATH" }, List.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).in(null, new String[] { "PATH" }, List.of("v1"));
    }

    @Test
    public void testLike_StringStringArgs_DelegatesArgumentsByWrappingInArrays() {
        SpecificationBuilder ret = builder.like("JOIN", "PATH", Set.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).like(new String[] { "JOIN" }, new String[] { "PATH" }, Set.of("v1"));
    }

    @Test
    public void testLike_StringArrayArg_DelegatesArgumentsByWrappingInArrays() {
        SpecificationBuilder ret = builder.like("JOIN", new String[] { "PATH" }, Set.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).like(new String[] { "JOIN" }, new String[] { "PATH" }, Set.of("v1"));
    }

    @Test
    public void testLike_ArrayStringArgs_DelegatesArgumentsByWrappingInArrays() {
        SpecificationBuilder ret = builder.like(new String[] { "JOIN" }, "PATH", Set.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).like(new String[] { "JOIN" }, new String[] { "PATH" }, Set.of("v1"));
    }

    @Test
    public void testLike_ArrayArrayArgs_DelegatesArguments() {
        SpecificationBuilder ret = builder.like(new String[] { "JOIN" }, new String[] { "PATH" }, Set.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).like(new String[] { "JOIN" }, new String[] { "PATH" }, Set.of("v1"));
    }

    @Test
    public void testLike_StringArgs_DelegatesArgumentsByWrappingInArraysAndNullForMissing() {
        SpecificationBuilder ret = builder.like("PATH", Set.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).like(null, new String[] { "PATH" }, Set.of("v1"));
    }

    @Test
    public void testLike_ArrayArgs_DelegatesArgumentsByWrappingInArraysAndNullForMissing() {
        SpecificationBuilder ret = builder.like(new String[] { "PATH" }, Set.of("v1"));

        assertSame(builder, ret);
        verify(mDelegate, times(1)).like(null, new String[] { "PATH" }, Set.of("v1"));
    }

    @Test
    public <T extends Comparable<T>> void testBetween_StringStringArgs_DelegatesArgumentsByWrappingInArrays() {
        T c1 = (T) mock(Comparable.class);
        T c2 = (T) mock(Comparable.class);

        SpecificationBuilder ret = builder.between("JOIN", "PATH", c1, c2);

        assertSame(builder, ret);
        verify(mDelegate, times(1)).between(new String[] { "JOIN" }, new String[] { "PATH" }, c1, c2);
    }

    @Test
    public <T extends Comparable<T>> void testBetween_StringArrayArg_DelegatesArgumentsByWrappingInArrays() {
        T c1 = (T) mock(Comparable.class);
        T c2 = (T) mock(Comparable.class);

        SpecificationBuilder ret = builder.between("JOIN", new String[] { "PATH" }, c1, c2);

        assertSame(builder, ret);
        verify(mDelegate, times(1)).between(new String[] { "JOIN" }, new String[] { "PATH" }, c1, c2);
    }

    @Test
    public <T extends Comparable<T>> void testBetween_ArrayStringArgs_DelegatesArgumentsByWrappingInArrays() {
        T c1 = (T) mock(Comparable.class);
        T c2 = (T) mock(Comparable.class);

        SpecificationBuilder ret = builder.between(new String[] { "JOIN" }, "PATH", c1, c2);

        assertSame(builder, ret);
        verify(mDelegate, times(1)).between(new String[] { "JOIN" }, new String[] { "PATH" }, c1, c2);
    }

    @Test
    public <T extends Comparable<T>> void testBetween_ArrayArrayArgs_DelegatesArguments() {
        T c1 = (T) mock(Comparable.class);
        T c2 = (T) mock(Comparable.class);

        SpecificationBuilder ret = builder.between(new String[] { "JOIN" }, new String[] { "PATH" }, c1, c2);

        assertSame(builder, ret);
        verify(mDelegate, times(1)).between(new String[] { "JOIN" }, new String[] { "PATH" }, c1, c2);
    }

    @Test
    public <T extends Comparable<T>> void testBetween_StringArgs_DelegatesArgumentsByWrappingInArraysAndNullForMissing() {
        T c1 = (T) mock(Comparable.class);
        T c2 = (T) mock(Comparable.class);

        SpecificationBuilder ret = builder.between("PATH", c1, c2);

        assertSame(builder, ret);
        verify(mDelegate, times(1)).between(null, new String[] { "PATH" }, c1, c2);
    }

    @Test
    public <T extends Comparable<T>> void testBetween_ArrayArgs_DelegatesArgumentsByWrappingInArraysAndNullForMissing() {
        T c1 = (T) mock(Comparable.class);
        T c2 = (T) mock(Comparable.class);

        SpecificationBuilder ret = builder.between(new String[] { "PATH" }, c1, c2);

        assertSame(builder, ret);
        verify(mDelegate, times(1)).between(null, new String[] { "PATH" }, c1, c2);
    }

    @Test
    public void testNot_CallsNotOnDelegate() {
        SpecificationBuilder ret = builder.not();

        assertSame(builder, ret);
        verify(mDelegate, times(1)).not();
    }

    @Test
    public void testBuild_ReturnsSpecificationFromDelegate() {
        Specification<Object> mSpec = mock(Specification.class);
        doReturn(mSpec).when(mDelegate).build();

        Specification<Object> spec = builder.build();

        assertSame(mSpec, spec);
    }
}
