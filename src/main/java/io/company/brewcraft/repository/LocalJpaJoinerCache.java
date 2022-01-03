package io.company.brewcraft.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.persistence.criteria.Path;

public class LocalJpaJoinerCache {
    private ThreadLocal<Map<Key<?, ?>, Path<?>>> cacheHolder;

    public LocalJpaJoinerCache() {
        this.cacheHolder = ThreadLocal.withInitial(() -> new HashMap<>());
    }

    public <X, Y> Path<X> get(Key<X, Y> key, Supplier<Path<X>> pathSupplier) {
        Path<X> attribute = null;
        Map<Key<?, ?>, Path<?>> cache = cacheHolder.get();

        if (!cache.containsKey(key)) {
            attribute = pathSupplier.get();
            cache.put(key, attribute);
        } else {
            attribute = (Path<X>) cache.get(key);
        }

        return attribute;
    }
}
