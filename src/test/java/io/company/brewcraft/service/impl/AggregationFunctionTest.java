package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.AverageSpec;
import io.company.brewcraft.service.CountSpec;
import io.company.brewcraft.service.MaxSpec;
import io.company.brewcraft.service.MinSpec;
import io.company.brewcraft.service.PathSpec;
import io.company.brewcraft.service.PathProvider;
import io.company.brewcraft.service.SumSpec;

public class AggregationFunctionTest {
    @Test
    public void testGetAggregation_SumFunctionReturnsSumAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        CriteriaSpec<? extends Number> aggr = AggregationFunction.SUM.getAggregation(mProvider);

        assertEquals(new SumSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_SumFunctionReturnsSumAggregation_WhenPathsArrayIsNotNull() {
        CriteriaSpec<? extends Number> aggr = AggregationFunction.SUM.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new SumSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_CountFunctionReturnsCountAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        CriteriaSpec<? extends Number> aggr = AggregationFunction.COUNT.getAggregation(mProvider);

        assertEquals(new CountSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_CountFunctionReturnsCountAggregation_WhenPathsArrayIsNotNull() {
        CriteriaSpec<? extends Number> aggr = AggregationFunction.COUNT.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new CountSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_AvgFunctionReturnsAvgAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        CriteriaSpec<? extends Number> aggr = AggregationFunction.AVG.getAggregation(mProvider);

        assertEquals(new AverageSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_AvgFunctionReturnsAvgAggregation_WhenPathsArrayIsNotNull() {
        CriteriaSpec<? extends Number> aggr = AggregationFunction.AVG.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new AverageSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_MaxFunctionReturnsMaxAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        CriteriaSpec<? extends Number> aggr = AggregationFunction.MAX.getAggregation(mProvider);

        assertEquals(new MaxSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_MaxFunctionReturnsMaxAggregation_WhenPathsArrayIsNotNull() {
        CriteriaSpec<? extends Number> aggr = AggregationFunction.MAX.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new MaxSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_MinFunctionReturnsMinAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        CriteriaSpec<? extends Number> aggr = AggregationFunction.MIN.getAggregation(mProvider);

        assertEquals(new MinSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_MinFunctionReturnsMinAggregation_WhenPathsArrayIsNotNull() {
        CriteriaSpec<? extends Number> aggr = AggregationFunction.MIN.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new MinSpec<>(new PathSpec<>(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }
}
