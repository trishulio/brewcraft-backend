package io.company.brewcraft.repository;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.service.Aggregation;

public class CriteriaSpecBuilderTest {

    private CriteriaSpecBuilder builder;
    private SpecAccumulator mAccumulator;

    @BeforeEach
    public void init() {
        mAccumulator = mock(SpecAccumulator.class);
        builder = new CriteriaSpecBuilder(mAccumulator);
    }

    @Test
    public void testIn_AddsInClauseToAccumulator_WhenInputsAreValid() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.in(new String[] { "join1" }, new String[] { "layer-1" }, List.of("V1", "V2"));
        builder.build();

        Join<String, Object> mJoin = mock(Join.class);
        Path<String> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mJoin).get("layer-1");

        Root<String> mRoot = mock(Root.class);
        doReturn(mJoin).when(mRoot).join("join1");

        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mLayer1).in(List.of("V1", "V2"));

        Predicate ret = (Predicate) captor.getValue().getExpression(mRoot, null, null);

        assertEquals(mInCollectionPredicate, ret);
        assertEquals(1, captor.getAllValues().size());

        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testIn_DoesNotAddsCriteria_WhenCollectionIsNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        Join<String, Object> mJoin = mock(Join.class);
        Path<String> mLayer1 = mock(Path.class);

        builder.in(new String[] { "join1" }, new String[] { "layer-1" }, null);
        builder.build();

        assertEquals(0, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testIn_ThrowsIllegalArgumentException_WhenFieldsArrayIsEmptyOrNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.in(new String[] { "join1" }, new String[] {}, List.of("V1"));
        builder.in(new String[] { "join1" }, (String[]) null, List.of("V1"));

        Root<?> mRoot = mock(Root.class);

        assertThrows(IllegalArgumentException.class, () -> captor.getAllValues().get(0).getExpression(mRoot, null, null));
        assertThrows(IllegalArgumentException.class, () -> captor.getAllValues().get(0).getExpression(mRoot, null, null));
    }

    @Test
    public void testNot_SetsAccumulatorNotToTrue() {
        builder.not();

        verify(mAccumulator).setIsNot(true);
    }

    @Test
    public void testBetween_DoesNotAddCriteria_WhenStartAndEndIsNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] { "join1" }, new String[] { "layer-1" }, null, null);

        assertEquals(0, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testBetween_AddBetweenCriteria_WhenStartAndEndAreBothNotNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] { "join1" }, new String[] { "layer-1" }, 10, 50);

        Join<String, Object> mJoin = mock(Join.class);
        Root<Integer> mRoot = mock(Root.class);
        doReturn(mJoin).when(mRoot).join("join1");

        Path<Integer> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mJoin).get("layer-1");

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mCriteriaBuilder).between(mLayer1, 10, 50);

        captor.getValue().getExpression(mRoot, null, mCriteriaBuilder);

        assertEquals(1, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testBetween_AddsGreaterThanEqualToCriteria_WhenEndIsNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] { "join1" }, new String[] { "layer-1" }, 10, null);

        Join<String, Object> mJoin = mock(Join.class);
        Root<Integer> mRoot = mock(Root.class);
        doReturn(mJoin).when(mRoot).join("join1");

        Path<Integer> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mJoin).get("layer-1");

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mCriteriaBuilder).greaterThanOrEqualTo(mLayer1, 10);

        captor.getValue().getExpression(mRoot, null, mCriteriaBuilder);

        assertEquals(1, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testBetween_AddsLessThanEqualToCriteria_WhenStartIsNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] { "join1" }, new String[] { "layer-1" }, null, 50);

        Join<String, Object> mJoin = mock(Join.class);
        Root<Integer> mRoot = mock(Root.class);
        doReturn(mJoin).when(mRoot).join("join1");

        Path<Integer> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mRoot).get("layer-1");

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mCriteriaBuilder).lessThanOrEqualTo(mLayer1, 50);

        captor.getValue().getExpression(mRoot, null, mCriteriaBuilder);

        assertEquals(1, captor.getAllValues().size());
        verify(mAccumulator, times(1)).setIsNot(false);
    }

    @Test
    public void testBetween_ThrowsException_WhenFieldsAreEmptyOrNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.between(new String[] { "join1" }, new String[] {}, 10, 20);
        builder.between(new String[] { "join1" }, null, 0, 1);

        Root<?> mRoot = mock(Root.class);

        assertThrows(IllegalArgumentException.class, () -> captor.getAllValues().get(0).getExpression(mRoot, null, null));
        assertThrows(IllegalArgumentException.class, () -> captor.getAllValues().get(0).getExpression(mRoot, null, null));
    }

    @Test
    public void testLike_AddLikeCriteriaQuery_WhenQueriesAreNotNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.like(new String[] { "join1" }, new String[] { "layer-1" }, Set.of("HELLO"));

        Join<String, Object> mJoin = mock(Join.class);
        Root<String> mRoot = mock(Root.class);
        doReturn(mJoin).when(mRoot).join("join1");

        Path<String> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mJoin).get("layer-1");

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mCriteriaBuilder).like(mLayer1, "%HELLO%");

        captor.getValue().getExpression(mRoot, null, mCriteriaBuilder);

        assertEquals(1, captor.getAllValues().size());
    }

    @Test
    public void testLike_AddLikeCriteriaForMultipleQuery_WhenQueriesAreNotNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder.like(new String[] { "join1" }, new String[] { "layer-1" }, Set.of("HELLO", "BYE", "FOO"));

        Join<String, Object> mJoin = mock(Join.class);
        Root<String> mRoot = mock(Root.class);
        doReturn(mJoin).when(mRoot).join("join1");

        Path<String> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mJoin).get("layer-1");

        Path<String> mLowerCase = mock(Path.class);
        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        doReturn(mLowerCase).when(mCriteriaBuilder).lower(mLayer1);

        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mCriteriaBuilder).like(eq(mLowerCase), anyString());

        assertEquals(3, captor.getAllValues().size());
        assertSame(mInCollectionPredicate, captor.getAllValues().get(0).getExpression(mRoot, null, mCriteriaBuilder));
        assertSame(mInCollectionPredicate, captor.getAllValues().get(1).getExpression(mRoot, null, mCriteriaBuilder));
        assertSame(mInCollectionPredicate, captor.getAllValues().get(2).getExpression(mRoot, null, mCriteriaBuilder));

        verify(mCriteriaBuilder, times(1)).like(mLowerCase, "%hello%");
        verify(mCriteriaBuilder, times(1)).like(mLowerCase, "%bye%");
        verify(mCriteriaBuilder, times(1)).like(mLowerCase, "%foo%");
    }

    @Test
    public void testLike_AddLikeCriteriaIgnoresNullQueries_WhenQueriesAreNotNull() {
        ArgumentCaptor<Aggregation> captor = ArgumentCaptor.forClass(Aggregation.class);
        doNothing().when(mAccumulator).add(captor.capture());

        Set<String> queries = new HashSet<String>();
        queries.add("HELLO");
        queries.add(null);
        queries.add("FOO");
        builder.like(new String[] { "join1" }, new String[] { "layer-1" }, queries);

        Join<String, Object> mJoin = mock(Join.class);
        Root<String> mRoot = mock(Root.class);
        doReturn(mJoin).when(mRoot).join("join1");

        Path<String> mLayer1 = mock(Path.class);
        doReturn(mLayer1).when(mJoin).get("layer-1");

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);

        Path<String> mLower = mock(Path.class);
        doReturn(mLower).when(mCriteriaBuilder).lower(mLayer1);

        Predicate mInCollectionPredicate = mock(Predicate.class);
        doReturn(mInCollectionPredicate).when(mCriteriaBuilder).like(eq(mLower), anyString());

        assertEquals(2, captor.getAllValues().size());
        assertSame(mInCollectionPredicate, captor.getAllValues().get(0).getExpression(mRoot, null, mCriteriaBuilder));
        assertSame(mInCollectionPredicate, captor.getAllValues().get(1).getExpression(mRoot, null, mCriteriaBuilder));

        verify(mCriteriaBuilder, times(1)).like(mLower, "%hello%");
        verify(mCriteriaBuilder, times(1)).like(mLower, "%foo%");
    }

    @Test
    public void testLike_DoesNotAddLikeCriteria_WhenQueriesAreNull() {
        builder.like(new String[] { "join" }, new String[] { "layer-1" }, null);
        verifyNoInteractions(mAccumulator);
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
