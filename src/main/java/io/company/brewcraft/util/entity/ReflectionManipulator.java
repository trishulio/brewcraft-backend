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

    public void copy(Object o1, Object o2, CheckedFunction<Boolean, PropertyDescriptor, ReflectiveOperationException> predicate) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Outer Joins can not be on null objects");
        }

        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(o2.getClass(), Object.class).getPropertyDescriptors();

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

    public Set<String> getPropertyNames(Object o) {
        Set<String> propertyNames = null;
        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(o.getClass(), Object.class).getPropertyDescriptors();

            propertyNames = Arrays.stream(pds).map(pd -> pd.getName()).collect(Collectors.toSet());
        } catch (IntrospectionException e) {
            String msg = String.format("Failed to introspect object because: %s", e.getMessage());
            handleException(msg, e);
        }

        return propertyNames;
    }

    public Set<String> getPropertyNames(Class<?> clazz) {
        Set<String> propertyNames = null;

        Method[] methods = clazz.getMethods();
        propertyNames = Arrays.stream(methods)
                        .filter(m -> m.getName().startsWith("get") || m.getName().startsWith("set"))
                        .map(method -> method.getName().replaceFirst("get|set", ""))
                        .map(prop -> {
                            char c = Character.toLowerCase(prop.charAt(0));
                            String l = Character.toString(c);
                            return prop.replaceFirst("\\w", l);
                        }).collect(Collectors.toSet());

        return propertyNames;
    }

    private void handleException(String msg, Exception e) {
        logger.error(msg);
        throw new RuntimeException(msg, e.getCause());
    }
}
