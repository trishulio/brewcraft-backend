package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.data.TriFunction;

@SuppressWarnings({ "unchecked" })
public class BasicSpecBuilderTest {

    private SpecificationBuilder builder;

    private SpecAccumulator mAccumulator;

    @BeforeEach
    public void init() {
        mAccumulator = mock(SpecAccumulator.class);
        builder = new BasicSpecBuilder(mAccumulator);
    }

    public void testBuilder_ReturnsANewInstanceOfBasicSpecBuilder() {
        SpecificationBuilder anotherBuilder = SpecificationBuilder.builder();

        assertNotSame(builder, anotherBuilder);
        assertTrue(builder instanceof BasicSpecBuilder, String.format("SpecificationBuilder.builder() unexpectedly returned an instance of class: %s", builder.getClass().getSimpleName()));
    }

    @Test
    public void testIn_AddsInClauseToAccumulator_WhenInputsAreValid() {
        ArgumentCaptor<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> captor = ArgumentCaptor.forClass(TriFunction.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.in(new String[] { "layer-1" }, List.of("V1", "V2")).build();

        Root<String> mRoot = mock(Root.class);
        Path<String> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mRoot).get("layer-1");

        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mLayer1).in(List.of("V1", "V2"));

        Predicate ret = captor.getValue().apply(mRoot, null, null);

        assertEquals(mInCollectionPredicate, ret);
        assertEquals(1, captor.getAllValues().size());

        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testIn_DoesNotAddsCriteria_WhenCollectionIsNull() {
        ArgumentCaptor<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> captor = ArgumentCaptor.forClass(TriFunction.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.in(new String[] { "layer-1" }, null).build();

        assertEquals(0, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testIn_ThrowsIllegalArgumentException_WhenFieldsArrayIsEmptyOrNull() {
        ArgumentCaptor<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> captor = ArgumentCaptor.forClass(TriFunction.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.in(new String[] {}, List.of("V1"));
        builder.in((String[]) null, List.of("V1"));

        Root<?> mRoot = mock(Root.class);

        assertThrows(IllegalArgumentException.class, () -> captor.getAllValues().get(0).apply(mRoot, null, null));
        assertThrows(IllegalArgumentException.class, () -> captor.getAllValues().get(0).apply(mRoot, null, null));
    }

    @Test
    public void testOverloadedIn_CallsInMethod_AfterConvertingTheArgument() {
        builder = spy(builder);
        builder.in("layer-1", List.of("V1", "V2"));

        verify(builder, times(1)).in(new String[] { "layer-1" }, List.of("V1", "V2"));
    }

    @Test
    public void testNot_SetsAccumulatorNotToTrue() {
        builder.not();

        verify(mAccumulator).setIsNot(true);
    }

    @Test
    public void testBetween_DoesNotAddCriteria_WhenStartAndEndIsNull() {
        ArgumentCaptor<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> captor = ArgumentCaptor.forClass(TriFunction.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] { "layer-1" }, null, null);

        assertEquals(0, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testBetween_AddBetweenCriteria_WhenStartAndEndAreBothNotNull() {
        ArgumentCaptor<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> captor = ArgumentCaptor.forClass(TriFunction.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] { "layer-1" }, 10, 50);

        Root<Integer> mRoot = mock(Root.class);
        Path<Integer> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mRoot).get("layer-1");

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mCriteriaBuilder).between(mLayer1, 10, 50);

        captor.getValue().apply(mRoot, null, mCriteriaBuilder);

        assertEquals(1, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testBetween_AddsGreaterThanEqualToCriteria_WhenEndIsNull() {
        ArgumentCaptor<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> captor = ArgumentCaptor.forClass(TriFunction.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] { "layer-1" }, 10, null);

        Root<Integer> mRoot = mock(Root.class);
        Path<Integer> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mRoot).get("layer-1");

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mCriteriaBuilder).greaterThanOrEqualTo(mLayer1, 10);

        captor.getValue().apply(mRoot, null, mCriteriaBuilder);

        assertEquals(1, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testBetween_AddsLessThanEqualToCriteria_WhenStartIsNull() {
        ArgumentCaptor<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> captor = ArgumentCaptor.forClass(TriFunction.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] { "layer-1" }, null, 50);

        Root<Integer> mRoot = mock(Root.class);
        Path<Integer> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mRoot).get("layer-1");

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mCriteriaBuilder).lessThanOrEqualTo(mLayer1, 50);

        captor.getValue().apply(mRoot, null, mCriteriaBuilder);

        assertEquals(1, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testBetween_ThrowsException_WhenFieldsAreEmptyOrNull() {
        ArgumentCaptor<TriFunction<Predicate, Root<?>, CriteriaQuery<?>, CriteriaBuilder>> captor = ArgumentCaptor.forClass(TriFunction.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] {}, 10, 20);
        builder.between((String[]) null, 0, 1);

        Root<?> mRoot = mock(Root.class);

        assertThrows(IllegalArgumentException.class, () -> captor.getAllValues().get(0).apply(mRoot, null, null));
        assertThrows(IllegalArgumentException.class, () -> captor.getAllValues().get(0).apply(mRoot, null, null));
    }

    @Test
    public void testOverloadedBetween_CallsBetweenFunction_WithArrayArgument() {
        builder = spy(builder);
        builder.between("layer-1", 0, 1);

        verify(builder, times(1)).between(new String[] { "layer-1" }, 0, 1);
    }

    @Test
    public void testBuild_ReturnSpecification_WithToPredicateFunctionThatGetsAllPredicatesAndCombinesThem() {
        Specification<Integer> spec = builder.build();

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        Root<Integer> mRoot = mock(Root.class);
        CriteriaQuery<?> mQuery = mock(CriteriaQuery.class);

        Predicate[] mPreds = new Predicate[] { mock(Predicate.class), mock(Predicate.class) };
        doReturn(mPreds).when(mAccumulator).getPredicates(mRoot, mQuery, mCriteriaBuilder);

        Predicate mCombined = mock(Predicate.class);
        doReturn(mCombined).when(mCriteriaBuilder).and(mPreds);

        Predicate ret = spec.toPredicate(mRoot, mQuery, mCriteriaBuilder);

        assertEquals(ret, mCombined);
    }
}
