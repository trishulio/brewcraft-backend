package io.company.brewcraft.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

public class ManagedEntityService<ID, E extends CrudEntity<ID>, BE, UE extends UpdatableEntity<ID>> extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ManagedEntityService.class);

    private final UtilityProvider utilProvider;
    private final Class<BE> baseEntityCls;
    private final Class<UE> updateEntityCls;
    private final Class<E> entityCls;

    public ManagedEntityService(UtilityProvider utilProvider, Class<BE> baseEntityCls, Class<UE> updateEntityCls, Class<E> entityCls) {
        this.utilProvider = utilProvider;
        this.baseEntityCls = baseEntityCls;
        this.updateEntityCls = updateEntityCls;
        this.entityCls = entityCls;
    }

    public List<E> getPutItems(List<E> existingItems, List<UE> updates) {
        final Validator validator = this.utilProvider.getValidator();

        if (updates == null) {
            return null;
        }

        final List<E> targetItems = new ArrayList<>();

        existingItems = existingItems == null ? new ArrayList<>(0) : existingItems;
        final Map<ID, E> idToItemLookup = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        updates.forEach(update -> {
            final E item = this.instance();
            Class<?> itemClazz = this.baseEntityCls;
            if (update.getId() != null) {
                final E existing = idToItemLookup.get(update.getId());
                if (validator.rule(existing != null, "No existing invoice item found with Id: %s.", update.getId())) {
                    existing.optimisticLockCheck(update);
                    itemClazz = this.updateEntityCls;
                }
            }
            item.override(update, this.getPropertyNames(itemClazz));
            item.setId(update.getId()); // TODO: During creation, this ID is ignored.
            item.setVersion(update.getVersion());
            targetItems.add(item);
        });

        validator.raiseErrors();
        return targetItems;
    }

    public List<E> getPatchItems(List<E> existingItems, List<UE> patches) {
        final Validator validator = this.utilProvider.getValidator();

        if (patches == null) {
            return existingItems;
        }

        existingItems = existingItems == null ? new ArrayList<>() : existingItems;
        final List<E> targetItems = new ArrayList<>(existingItems.size());


        final Map<ID, E> idToItemLookup = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        patches.forEach(patch -> {
            final E item = this.instance();
            final E existing = idToItemLookup.get(patch.getId());
            if (validator.rule(existing != null, "No existing invoice item found with Id: %s.", patch.getId())) {
                existing.optimisticLockCheck(patch);
                item.override(existing, this.getPropertyNames(this.entityCls));
            }
            item.outerJoin(patch, this.getPropertyNames(this.updateEntityCls));
            item.setId(patch.getId()); // TODO: During creation, this ID is ignored.
            item.setVersion(patch.getVersion());
            targetItems.add(item);
        });

        validator.raiseErrors();
        return targetItems;
    }

    public List<E> getAddItems(Collection<BE> additions) {
        List<E> targetItems = null;
        if (additions != null) {
            targetItems = additions.stream().map(i -> {
                final E item = this.instance();
                log.debug("Applying properties of E: {} to new item", i);
                item.override(i, this.getPropertyNames(this.baseEntityCls));
                return item;
            }).collect(Collectors.toList());
        }

        return targetItems;
    }

    private E instance() {
        try {
            return this.entityCls.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            final String msg = String.format("Failed to create an instance of class '%s' because %s", this.entityCls.getName(), e.getMessage());
            log.error(msg);
            throw new RuntimeException(msg, e);
        }
    }
}
