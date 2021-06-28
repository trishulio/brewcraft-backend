package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.Aggregation;

public class SpecAccumulatorTest {

    private SpecAccumulator accumulator;

    @BeforeEach
    public void init() {
        accumulator = new SpecAccumulator();
    }

    @Test
    public void testAdd_ConvertsSpecIntoAPredicateAndAddsToTheList_WhenIsNotIsFalse() {
        Predicate mPred = mock(Predicate.class);
        Aggregation spec = (root, query, cb) -> mPred;

        CriteriaBuilder mCb = mock(CriteriaBuilder.class);
        Predicate mCombined = mock(Predicate.class);
        doReturn(mCombined).when(mCb).and(mPred);

        accumulator.add(spec);

        Predicate[] preds = accumulator.getPredicates(null, null, mCb);
        assertEquals(1, preds.length);
        assertEquals(mCombined, preds[0]);
    }

    @Test
    public void testNotAdd_ConvertsSpecIntoNegativePredicateAndAddsToTheList_WhenIsNotIsTrue() {
        Predicate mPred = mock(Predicate.class);
        Aggregation spec = (root, query, cb) -> mPred;

        CriteriaBuilder mCb = mock(CriteriaBuilder.class);
        Predicate mNegation = mock(Predicate.class);
        doReturn(mNegation).when(mCb).not(mPred);

        Predicate mCombined = mock(Predicate.class);
        doReturn(mCombined).when(mCb).and(mNegation);

        accumulator.setIsNot(true);
        accumulator.add(spec);

        Predicate[] preds = accumulator.getPredicates(null, null, mCb);
        assertEquals(1, preds.length);
        assertEquals(mCombined, preds[0]);
    }

    @Test
    public void testGetPredicates_ReturnsArrayOfAllConvertedPredicatesAdded() {
        CriteriaBuilder mCb = mock(CriteriaBuilder.class);

        Predicate mPred1 = mock(Predicate.class);
        Aggregation spec1 = (root, query, cb) -> mPred1;
        Predicate mCombined1 = mock(Predicate.class);
        doReturn(mCombined1).when(mCb).and(mPred1);

        Predicate mPred2 = mock(Predicate.class);
        Aggregation spec2 = (root, query, cb) -> mPred2;
        Predicate mCombined2 = mock(Predicate.class);
        doReturn(mCombined2).when(mCb).and(mPred2);

        accumulator.add(spec1);
        accumulator.add(spec2);

        Predicate[] preds = accumulator.getPredicates(null, null, mCb);
        assertEquals(2, preds.length);
        assertEquals(mCombined1, preds[0]);
        assertEquals(mCombined2, preds[1]);
    }
}
