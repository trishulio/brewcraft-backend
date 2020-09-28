package io.company.brewcraft.security.store;

import java.io.IOException;

public interface SecretsManager<K, V> {

    public V get(K secretId) throws IOException;

    public void put(K secretId, V secret) throws IOException;

}
