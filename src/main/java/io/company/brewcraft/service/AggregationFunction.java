package io.company.brewcraft.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/***
 * TODO: This is a handy approach but might not be a scalable solution.
 * Bottleneck occurs when aggregations are decorated or are on multiple
 * fields.
 *
 * How aggregations are used will become more clear when their usage increases.
 * For now, these enums are used temporarily.
 */

public enum AggregationFunction {
    SUM(SumAggregation.class),
    COUNT(CountAggregation.class),
    AVG(AverageAggregation.class),
    MAX(MaxAggregation.class),
    MIN(MinAggregation.class);

    private Class<? extends Aggregation> clazz;

    private AggregationFunction(Class<? extends Aggregation> clazz) {
        this.clazz = clazz;
    }

    public Aggregation getAggregation(PathProvider provider) {
        return getAggregation(provider.getPath());
    }

    public Aggregation getAggregation(String... path) {
        try {
            Constructor<? extends Aggregation> constructor = this.clazz.getConstructor(Aggregation.class);

            return constructor.newInstance(new PathAggregation(path));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            String msg = String.format("Failed to create an instance of type: '%s' with path: %s because: '%s'", this.clazz.getName(), Arrays.toString(path).replace(", ", "/"), e.getMessage());

            throw new RuntimeException(msg, e);
        }
    }
}