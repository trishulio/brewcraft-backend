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
    SUM(SumSpec.class),
    COUNT(CountSpec.class),
    AVG(AverageSpec.class),
    MAX(MaxSpec.class),
    MIN(MinSpec.class);

    private Class<? extends CriteriaSpec<? extends Number>> clazz;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private AggregationFunction(Class<? extends CriteriaSpec> clazz) {
        this.clazz = (Class<? extends CriteriaSpec<? extends Number>>) clazz;
    }

    public CriteriaSpec<? extends Number> getAggregation(PathProvider provider) {
        return getAggregation(provider.getPath());
    }

    public  CriteriaSpec<? extends Number> getAggregation(String... path) {
        try {
            Constructor<? extends CriteriaSpec<? extends Number>> constructor = this.clazz.getConstructor(CriteriaSpec.class);

            return constructor.newInstance(new SelectColumnSpec<>(path));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            String msg = String.format("Failed to create an instance of type: '%s' with path: %s because: '%s'", this.clazz.getName(), Arrays.toString(path).replace(", ", "/"), e.getMessage());

            throw new RuntimeException(msg, e);
        }
    }
}