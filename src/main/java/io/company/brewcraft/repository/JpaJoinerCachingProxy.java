package io.company.brewcraft.repository;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

import io.company.brewcraft.model.BaseModel;

public class JpaJoinerCachingProxy implements JpaJoiner {
    private JpaJoinerLocalCache cache;
    private JpaJoiner cjProcessor;

    public JpaJoinerCachingProxy(JpaJoinerLocalCache cache, JpaJoiner cjProcessor) {
        this.cache = cache;
        this.cjProcessor = cjProcessor;
    }

    @Override
    public <X, Y> From<X, Y> join(From<X, Y> join, String fieldName) {
        Key<X, Y> key = new Key<>(join, fieldName);

        return (From<X, Y>) this.cache.get(key, () -> (Path<X>) this.cjProcessor.join(join, fieldName));
    }

    @Override
    public <X, Y> Path<X> get(From<X, Y> join, String fieldName) {
        Key<X, Y> key = new Key<>(join, fieldName);

        return this.cache.get(key, () -> this.cjProcessor.get(join, fieldName));
    }
}

class Key<X, Y> extends BaseModel {
    private From<X, Y> join;
    private String fieldName;

    public Key(From<X, Y> join, String fieldName) {
        this.join = join;
        this.fieldName = fieldName;
    }
}