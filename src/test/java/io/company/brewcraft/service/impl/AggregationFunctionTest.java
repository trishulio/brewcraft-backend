package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.Aggregation;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.AverageAggregation;
import io.company.brewcraft.service.CountAggregation;
import io.company.brewcraft.service.MaxAggregation;
import io.company.brewcraft.service.MinAggregation;
import io.company.brewcraft.service.PathAggregation;
import io.company.brewcraft.service.PathProvider;
import io.company.brewcraft.service.SumAggregation;

public class AggregationFunctionTest {
    @Test
    public void testGetAggregation_SumFunctionReturnsSumAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        Aggregation aggr = AggregationFunction.SUM.getAggregation(mProvider);

        assertEquals(new SumAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_SumFunctionReturnsSumAggregation_WhenPathsArrayIsNotNull() {
        Aggregation aggr = AggregationFunction.SUM.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new SumAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_CountFunctionReturnsCountAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        Aggregation aggr = AggregationFunction.COUNT.getAggregation(mProvider);

        assertEquals(new CountAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_CountFunctionReturnsCountAggregation_WhenPathsArrayIsNotNull() {
        Aggregation aggr = AggregationFunction.COUNT.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new CountAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_AvgFunctionReturnsAvgAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        Aggregation aggr = AggregationFunction.AVG.getAggregation(mProvider);

        assertEquals(new AverageAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_AvgFunctionReturnsAvgAggregation_WhenPathsArrayIsNotNull() {
        Aggregation aggr = AggregationFunction.AVG.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new AverageAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_MaxFunctionReturnsMaxAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        Aggregation aggr = AggregationFunction.MAX.getAggregation(mProvider);

        assertEquals(new MaxAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_MaxFunctionReturnsMaxAggregation_WhenPathsArrayIsNotNull() {
        Aggregation aggr = AggregationFunction.MAX.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new MaxAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_MinFunctionReturnsMinAggregation_WhenPathProviderIsNotNull() {
        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] { "FIELD_1", "FIELD_2" }).when(mProvider).getPath();

        Aggregation aggr = AggregationFunction.MIN.getAggregation(mProvider);

        assertEquals(new MinAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }

    @Test
    public void testGetAggregation_MinFunctionReturnsMinAggregation_WhenPathsArrayIsNotNull() {
        Aggregation aggr = AggregationFunction.MIN.getAggregation("FIELD_1", "FIELD_2");

        assertEquals(new MinAggregation(new PathAggregation(new String[] { "FIELD_1", "FIELD_2" })), aggr);
    }
}
