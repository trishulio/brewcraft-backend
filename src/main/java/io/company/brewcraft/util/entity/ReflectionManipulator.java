package io.company.brewcraft.util.entity;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.data.CheckedFunction;

public class ReflectionManipulator {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionManipulator.class);

    public boolean equals(Object o, Object that) {
        return EqualsBuilder.reflectionEquals(o, that);
    }

    public static final ReflectionManipulator INSTANCE = new ReflectionManipulator();

    public void outerJoin(Object o1, Object o2, CheckedFunction<Boolean, PropertyDescriptor, ReflectiveOperationException> predicate) {
        if (o1 == null || o2 == null || o1.getClass() != o2.getClass()) {
            throw new IllegalArgumentException("Outer Joins can not be on null objects or objects of different classes");
        }

        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(o1.getClass(), Object.class).getPropertyDescriptors();

            for (PropertyDescriptor pd : pds) {
                Method getter = pd.getReadMethod();
                Method setter = pd.getWriteMethod();

                if (getter == null || setter == null) {
                    continue;
                }

                boolean pass = predicate.apply(pd);

                if (pass) {
                    Object value = getter.invoke(o2);
                    setter.invoke(o1, value);
                }
            }

        } catch (IntrospectionException e) {
            String msg = String.format("Failed to introspect object because: %s", e.getMessage());
            handleException(msg, e);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            String msg = String.format("Failed to access the value using dynamic method because: %s", e.getMessage());
            handleException(msg, e);
        } catch (ReflectiveOperationException e) {
            String msg = String.format("Failed to execute the predicate because: %s", e.getMessage());
            handleException(msg, e);
        }
    }

    public Set<String> getPropertyNames(Class<?> clazz) {
        Set<String> propertyNames = null;
        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();

            propertyNames = Arrays.stream(pds).map(pd -> pd.getName()).collect(Collectors.toSet());
        } catch (IntrospectionException e) {
            String msg = String.format("Failed to introspect object because: %s", e.getMessage());
            handleException(msg, e);
        }

        return propertyNames;
    }

    private void handleException(String msg, Exception e) {
        logger.error(msg);
        throw new RuntimeException(msg, e.getCause());
    }
}
