package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

public class CollectionAccessorRefresher<I, A, V extends Identified<I>> {
    private static final Logger log = LoggerFactory.getLogger(AccessorRefresher.class);

    private Class<V> clazz;
    private Function<A, Collection<V>> getter;
    private BiConsumer<A, Collection<V>> setter;
    private Function<Iterable<I>, List<V>> entityRetriever;

    public CollectionAccessorRefresher(Class<V> clazz, Function<A, Collection<V>> getter, BiConsumer<A, Collection<V>> setter, Function<Iterable<I>, List<V>> entityRetriever) {
        this.clazz = clazz;
        this.getter = getter;
        this.setter = setter;
        this.entityRetriever = entityRetriever;
    }

    public void refreshAccessors(Collection<? extends A> accessors) {
        if (accessors != null && accessors.size() > 0) {

            final Map<A, Set<I>> entityToCollectionEntitiesIds = new HashMap<>();
            final Set<I> allCollectionEntitiesIds = new HashSet<>();
                    
            accessors.stream().filter(accessor -> accessor != null && getter.apply(accessor) != null && !getter.apply(accessor).isEmpty())
                     .forEach(accessor -> {
                         Set<I> collectionEntitiesIds = getter.apply(accessor).stream().map(collectionEntity -> collectionEntity.getId()).collect(Collectors.toSet());
                         entityToCollectionEntitiesIds.put(accessor, collectionEntitiesIds);
                         allCollectionEntitiesIds.addAll(collectionEntitiesIds);
                     });
                        
            final List<V> collectionEntities = entityRetriever.apply(allCollectionEntitiesIds);
            
            if(collectionEntities.size() != allCollectionEntitiesIds.size()) {
                List<?> existingCollectionEntitiesIds = collectionEntities.stream().map(entity -> entity.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all %ss in Id-Set: %s. Only found the ones with Ids: %s", this.clazz.getSimpleName(), allCollectionEntitiesIds, existingCollectionEntitiesIds));
             }
            
            final Map<I, V> idToCollectionEntity = collectionEntities.stream().collect(Collectors.toMap(entity -> entity.getId(), Function.identity()));
            
            for (var entry : entityToCollectionEntitiesIds.entrySet()) {
                A entity = entry.getKey();
                final Set<I> collectionIds = entry.getValue();
                
                final Collection<V> refreshedCollectionEntities = collectionIds.stream().map(idToCollectionEntity::get).collect(Collectors.toList());
                setter.accept(entity, refreshedCollectionEntities);
            }
        }
    }
}
