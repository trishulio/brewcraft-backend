package io.company.brewcraft.repository;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.From;

import io.company.brewcraft.model.BaseModel;

public class LocalCachedCriteriaJoinProcessor implements CriteriaJoinProcessor {
    public static final Integer EXPECTED_MAX_SEARCH_LAYERS = 20;
    private ThreadLocal<Map<Key<?, ?>, From<?, ?>>> cacheHolder;

    private CriteriaJoinProcessor cjProcessor;

    public LocalCachedCriteriaJoinProcessor(CriteriaJoinProcessor cjProcessor) {
        this.cacheHolder = ThreadLocal.withInitial(() -> new HashMap<>(EXPECTED_MAX_SEARCH_LAYERS));
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