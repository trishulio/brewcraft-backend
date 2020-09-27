package io.company.brewcraft.security.store;

public interface KvStore<K, V> {

    V get(K key);

    V get(K key, V def);

    void set(K key, V value);
}
