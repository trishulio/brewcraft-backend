package io.company.brewcraft.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.company.brewcraft.security.store.KvStore;

public class InMemoryKvStore<K, V> implements KvStore<K, V> {

    private Map<K, V> data;

    public InMemoryKvStore() {
        this.data = new ConcurrentHashMap<>();
    }

    @Override
    public V get(K key) {
        return this.data.get(key);
    }

    @Override
    public V get(K key, V def) {
        return this.data.getOrDefault(key, def);
    }

    @Override
    public void set(K key, V value) {
        this.data.put(key, value);
    }
}
