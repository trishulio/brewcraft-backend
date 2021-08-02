package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class AccessorRefresher<I, A, V extends Identified<I>> {
    private static final Logger log = LoggerFactory.getLogger(AccessorRefresher.class);
    
    private Class<V> clazz;
    private Function<A, V> getter;
    private BiConsumer<A, V> setter;
    private Function<Iterable<I>, List<V>> entityRetriever;

    public AccessorRefresher(Class<V> clazz, Function<A, V> getter, BiConsumer<A, V> setter, Function<Iterable<I>, List<V>> entityRetriever) {
        this.clazz = clazz;
        this.getter = getter;
        this.setter = setter;
        this.entityRetriever = entityRetriever;
    }

    public void refreshAccessors(Collection<? extends A> accessors) {
        if (accessors != null && accessors.size() > 0) {
            Map<I, List<A>> lookupAccessorsByValueId = accessors.stream().filter(accessor -> accessor != null && getter.apply(accessor) != null).collect(Collectors.groupingBy(accessor -> getter.apply(accessor).getId()));

            List<V> entities = entityRetriever.apply(lookupAccessorsByValueId.keySet());

            if (lookupAccessorsByValueId.keySet().size() != entities.size()) {
                List<?> entityIds = entities.stream().map(entity -> entity.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all %s in Id-Set: %s. Entities found with Ids: %s", this.clazz.getName(), lookupAccessorsByValueId.keySet(), entityIds));
            }

            accessors.forEach(accessor -> setter.accept(accessor, null));
            entities.forEach(value -> lookupAccessorsByValueId.get(value.getId()).forEach(accessor -> setter.accept(accessor, value)));
        }
    }
}
