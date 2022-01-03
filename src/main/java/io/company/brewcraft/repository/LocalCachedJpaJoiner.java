package io.company.brewcraft.repository;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.From;

import io.company.brewcraft.model.BaseModel;

public class LocalCachedJpaJoiner implements JpaJoiner {
    private ThreadLocal<Map<Key<?, ?>, From<?, ?>>> cacheHolder;

    private JpaJoiner cjProcessor;

    public LocalCachedJpaJoiner(JpaJoiner cjProcessor) {
        this.cacheHolder = ThreadLocal.withInitial(() -> new HashMap<>());
        this.cjProcessor = cjProcessor;
    }

    @Override
    public <X, Y> From<X, Y> apply(From<X, Y> join, Class<? extends Y> clazz, String fieldName) {
        Map<Key<?, ?>, From<?, ?>> cache = cacheHolder.get();

        Key<X, Y> key = new Key<>(join, clazz, fieldName);

        if (!cache.containsKey(key)) {
            join = this.cjProcessor.apply(join, clazz, fieldName);
            cache.put(key, join);
        } else {
            join = (From<X, Y>) cache.get(key);
        }

        return join;
    }
}

class Key<X, Y> extends BaseModel {
    private From<X, Y> join;
    Class<? extends Y> clazz;
    String fieldName;

    public Key(From<X, Y> join, Class<? extends Y> clazz, String fieldName) {
        this.join = join;
        this.clazz = clazz;
        this.fieldName = fieldName;
    }
}