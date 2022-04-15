package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.BaseEquipment;
import io.company.brewcraft.dto.UpdateEquipment;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class EquipmentService extends BaseService implements CrudService<Long, Equipment, BaseEquipment, UpdateEquipment, EquipmentAccessor> {
    private static final Logger log = LoggerFactory.getLogger(EquipmentService.class);

    private final UpdateService<Long, Equipment, BaseEquipment, UpdateEquipment> updateService;
    private final RepoService<Long, Equipment, EquipmentAccessor> repoService;

    public EquipmentService(UpdateService<Long, Equipment, BaseEquipment, UpdateEquipment> updateService, RepoService<Long, Equipment, EquipmentAccessor> repoService) {
        this.updateService = updateService;
        this.repoService = repoService;
    }

    public Page<Equipment> getEquipment(
            Set<Long> ids,
            Set<Long> excludeIds,
            Set<Long> facilityIds,
            Set<Long> typeIds,
            int page,
            int size,
            SortedSet<String> sort,
            boolean orderAscending
         ) {
        final Specification<Equipment> spec = WhereClauseBuilder
                                                .builder()
                                                .in(Equipment.FIELD_ID, ids)
                                                .not().in(Equipment.FIELD_ID, excludeIds)
                                                .in(new String[] { Equipment.FIELD_FACILITY, Facility.FIELD_ID }, facilityIds)
                                                .in(new String[] { Equipment.FIELD_TYPE, EquipmentType.FIELD_ID }, typeIds)
                                                .build();

        return this.repoService.getAll(spec, sort, orderAscending, page, size);
    }

    @Override
    public Equipment get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<Equipment> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<Equipment> getByAccessorIds(Collection<? extends EquipmentAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getEquipment());
    }

    @Override
    public boolean exists(Set<Long> ids) {
        return this.repoService.exists(ids);
    }

    @Override
    public boolean exist(Long id) {
        return this.repoService.exists(id);
    }

    @Override
    public long delete(Set<Long> ids) {
        long deleteCount = this.repoService.delete(ids);

        return deleteCount;
    }

    @Override
    public long delete(Long id) {
        return this.delete(Set.of(id));
    }

    @Override
    public List<Equipment> add(final List<BaseEquipment> additions) {
        if (additions == null) {
            return null;
        }

        final List<Equipment> entities = this.updateService.getAddEntities(additions);

        List<Equipment> equipment = this.repoService.saveAll(entities);

        return equipment;
    }

    @Override
    public List<Equipment> put(List<UpdateEquipment> updates) {
        if (updates == null) {
            return null;
        }

        final List<Equipment> existing = this.repoService.getByIds(updates);
        final List<Equipment> updated = this.updateService.getPutEntities(existing, updates);

        List<Equipment> equipment = this.repoService.saveAll(updated);

        return equipment;
    }

    @Override
    public List<Equipment> patch(List<UpdateEquipment> patches) {
        if (patches == null) {
            return null;
        }

        final List<Equipment> existing = this.repoService.getByIds(patches);

        if (existing.size() != patches.size()) {
            final Set<Long> existingIds = existing.stream().map(equipment -> equipment.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patches.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find equipment with Ids: %s", nonExistingIds));
        }

        final List<Equipment> updated = this.updateService.getPatchEntities(existing, patches);

        return this.repoService.saveAll(updated);
    }
}