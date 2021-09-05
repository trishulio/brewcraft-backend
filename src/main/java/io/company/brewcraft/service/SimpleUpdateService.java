package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

public class SimpleUpdateService <ID, E extends CrudEntity<ID>, BE, UE extends UpdatableEntity<ID>> extends BaseService implements UpdateService<ID, E, BE, UE> {
    private static final Logger log = LoggerFactory.getLogger(SimpleUpdateService.class);

    private final UtilityProvider utilProvider;

    private final Class<? extends BE> baseEntityCls;
    private final Class<? extends UE> updateEntityCls;
    private final Class<E> entityCls;
    private final Set<String> excludeProps;

    public SimpleUpdateService(UtilityProvider utilProvider, Class<? extends BE> baseEntityCls, Class<? extends UE> updateEntityCls, Class<E> entityCls, Set<String> excludeProps) {
        this.utilProvider = utilProvider;
        this.baseEntityCls = baseEntityCls;
        this.updateEntityCls = updateEntityCls;
        this.entityCls = entityCls;
        this.excludeProps = excludeProps;
    }

    @Override
    public List<E> getAddEntities(List<BE> additions) {
        if (additions == null) {
            return null;
        }

        return additions.stream().map(addition -> {
            final E item = this.newEntity();
            item.override(addition, this.getPropertyNames(this.baseEntityCls, this.excludeProps));
            return item;
         }).collect(Collectors.toList());
    }

    @Override
    public List<E> getPutEntities(List<E> existingItems, List<UE> updates) {
        if (updates == null) {
            return null;
        }

        final Validator validator = this.utilProvider.getValidator();

        existingItems = existingItems != null ? existingItems : new ArrayList<>(0);
        final Map<ID, E> idToItemLookup = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        final List<E> targetItems = updates.stream().map(update -> {
            final E item = this.newEntity();
            Class<?> itemCls = this.baseEntityCls;
            if (update.getId() != null) {
                final E existing = idToItemLookup.get(update.getId());
                if (validator.rule(existing != null, "No existing %s found with Id: %s.", this.entityCls.getSimpleName(), update.getId())) {
                    existing.optimisticLockCheck(update);
                    itemCls = this.updateEntityCls;
                }
            }
            item.override(update, this.getPropertyNames(itemCls, this.excludeProps));
            item.setId(update.getId()); // TODO: During creation, this ID is ignored.
            return item;
        }).collect(Collectors.toList());

        validator.raiseErrors();
        return targetItems;
    }

    @Override
    public List<E> getPatchEntities(List<E> existingItems, List<UE> patches) {
        final Validator validator = this.utilProvider.getValidator();

        List<E> targetItems = null;
        patches = patches == null ? new ArrayList<>() : patches;

        final Map<ID, UE> idToItemLookup = patches.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        if (existingItems != null) {
            targetItems = existingItems.stream().map(existing -> {
                final UE patch = idToItemLookup.remove(existing.getId());
                final E item = this.newEntity();
                item.override(existing, this.getPropertyNames(this.entityCls, this.excludeProps));
                if (patch != null) {
                    existing.optimisticLockCheck(patch);
                    item.outerJoin(patch, this.getPropertyNames(this.updateEntityCls, this.excludeProps));
                }
                item.setId(existing.getId());
                return item;
            }).collect(Collectors.toList());
        }

        idToItemLookup.forEach((id, patch) -> validator.rule(false, "Cannot apply the patch with Id: %s to an existing entity as it does not exist", id));

        validator.raiseErrors();
        return targetItems;
    }

    private E newEntity() {
        return this.util.construct(this.entityCls);
    }
}
