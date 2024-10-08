package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.service.BetweenSpec;
import io.company.brewcraft.service.ColumnSpec;
import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.InSpec;
import io.company.brewcraft.service.IsNullSpec;
import io.company.brewcraft.service.LikeSpec;

public class WhereClauseBuilderDelegateTest {
    private WhereClauseBuilderDelegate builder;
    private PredicateSpecAccumulator mAccumulator;

    private ArgumentCaptor<CriteriaSpec<Boolean>> captor;

    @BeforeEach
    public void init() {
        mAccumulator = mock(PredicateSpecAccumulator.class);
        captor = ArgumentCaptor.forClass(CriteriaSpec.class);
        doNothing().when(mAccumulator).add(captor.capture());

        builder = new WhereClauseBuilderDelegate(mAccumulator);
    }

    @Test
    public void testIsNull_AddsIsNullSpecAndResetNotFlag() {
        builder.isNull(new String[] { "layer-1" });

        CriteriaSpec<Boolean> expected = new IsNullSpec(new ColumnSpec(new String[] { "layer-1" }));
        assertEquals(expected, captor.getValue());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testIn_AddsInSpecAndResetNotFlag_WhenCollectionIsNotNull() {
        builder.in(new String[] { "layer-1" }, List.of("V1", "V2"));

        CriteriaSpec<Boolean> expected = new InSpec<>(new ColumnSpec<>(new String[] { "layer-1" }), List.of("V1", "V2"));
        assertEquals(expected, captor.getValue());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testIn_AddsNothingAndResetNotFlag_WhenCollectionIsNull() {
        builder.in(new String[] { "layer-1" }, null);

        assertEquals(List.of(), captor.getAllValues());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testLike_AddsLikeSpecAndResetFlag_WhenCollectionIsNotNull() {
        builder.like(new String[] { "layer-1" }, Set.of("V1", "V2"));

        List<CriteriaSpec<Boolean>> expected = List.of(
            new LikeSpec(new ColumnSpec<>(new String[] { "layer-1" }), "V1"),
            new LikeSpec(new ColumnSpec<>(new String[] { "layer-1" }), "V2")
        );

        Assertions.assertThat(expected).hasSameElementsAs(captor.getAllValues());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testLike_AddsNothingAndResetFlag_WhenCollectionIsNull() {
        builder.like(new String[] { "layer-1" }, null);

        assertEquals(List.of(), captor.getAllValues());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testBetween_AddsBetweenSpecAndResetFlag_WhenStartAndEndAreNotNull() {
        builder.between(new String[] { "layer-1" }, LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1));

        CriteriaSpec<Boolean> expected = new BetweenSpec<>(new ColumnSpec<>(new String[] { "layer-1" }), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1));

        assertEquals(expected, captor.getValue());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testBetween_AddsNothingAndResetFlag_WhenStartAndEndAreNull() {
        builder.between(new String[] { "layer-1" }, null, null);

        assertEquals(List.of(), captor.getAllValues());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testPredicate_callsSetIsPredicate() {
        builder.predicate(true);

        verify(mAccumulator).setIsPredicate(true);
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
