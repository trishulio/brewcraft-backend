package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class AccessorRefresher<A, V extends Identified<?>> {
    private static final Logger log = LoggerFactory.getLogger(AccessorRefresher.class);
    
    private Function<A, V> getter;
    private BiConsumer<A, V> setter;
    private Function<Set<?>, List<V>> entityRetriever;

    public AccessorRefresher(Function<A, V> getter, BiConsumer<A, V> setter, Function<Set<?>, List<V>> entityRetriever) {
        this.getter = getter;
        this.setter = setter;
        this.entityRetriever = entityRetriever;
    }
    
    public void refreshAccessors(Collection<? extends A> accessors) {
        if (accessors != null && accessors.size() > 0) {
            Map<?, List<A>> lookupAccessorsByValueId = accessors.stream().filter(accessor -> accessor != null && getter.apply(accessor) != null).collect(Collectors.groupingBy(accessor -> getter.apply(accessor).getId()));
            log.debug("accessMap: {}", lookupAccessorsByValueId);
            
            List<V> values = entityRetriever.apply(lookupAccessorsByValueId.keySet());
            log.debug("Entities: {}", values);

            if (lookupAccessorsByValueId.keySet().size() != values.size()) {
                List<?> valueIds = values.stream().map(material -> material.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all objects in Id-Set: %s. Objects found with Ids: %s", lookupAccessorsByValueId.keySet(), valueIds));
            }

            accessors.forEach(accessor -> setter.accept(accessor, null));
            values.forEach(value -> lookupAccessorsByValueId.get(value.getId()).forEach(accessor -> setter.accept(accessor, value)));
        }
    }
}
