package io.company.brewcraft.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentAccessor;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.CrudService;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class ShipmentService extends BaseService implements CrudService<Long, Shipment, BaseShipment<? extends BaseMaterialLot<?>>, UpdateShipment<? extends UpdateMaterialLot<?>>, ShipmentAccessor> {
    private static final Logger log = LoggerFactory.getLogger(ShipmentService.class);

    private final UpdateService<Long, Shipment, BaseShipment<? extends BaseMaterialLot<?>>, UpdateShipment<? extends UpdateMaterialLot<?>>> updateService;
    private final MaterialLotService lotService;
    private final RepoService<Long, Shipment, ShipmentAccessor> repoService;

    public ShipmentService(UpdateService<Long, Shipment, BaseShipment<? extends BaseMaterialLot<?>>, UpdateShipment<? extends UpdateMaterialLot<?>>> updateService, MaterialLotService lotService, RepoService<Long, Shipment, ShipmentAccessor> repoService) {
        this.updateService = updateService;
        this.lotService = lotService;
        this.repoService = repoService;
    }

    public Page<Shipment> getShipments(
        Set<Long> ids,
        Set<Long> excludeIds,
        Set<String> shipmentNumbers,
        Set<String> descriptions,
        Set<Long> statusIds,
        LocalDateTime deliveryDueDateFrom,
        LocalDateTime deliveryDueDateTo,
        LocalDateTime deliveredDateFrom,
        LocalDateTime deliveredDateTo,
        SortedSet<String> sort,
        boolean orderAscending,
        int page,
        int size
    ) {
        final Specification<Shipment> spec= WhereClauseBuilder.builder()
                                                          .in(Shipment.FIELD_ID, ids)
                                                          .not().in(Shipment.FIELD_ID, excludeIds)
                                                          .in(Shipment.FIELD_SHIPMENT_NUMBER, shipmentNumbers)
                                                          .in(Shipment.FIELD_DESCRIPTION, descriptions)
                                                          .in(new String[] {Shipment.FIELD_STATUS, ShipmentStatus.FIELD_ID}, statusIds)
                                                          .between(Shipment.FIELD_DELIVERY_DUE_DATE, deliveryDueDateFrom, deliveryDueDateTo)
                                                          .between(Shipment.FIELD_DELIVERED_DATE, deliveredDateFrom, deliveredDateTo)
                                                          .build();

        return this.repoService.getAll(spec, sort, orderAscending, page, size);
    }

    @Override
    public Shipment get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<Shipment> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<Shipment> getByAccessorIds(Collection<? extends ShipmentAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getShipment());
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
    public int delete(Set<Long> ids) {
        return this.repoService.delete(ids);
    }

    @Override
    public int delete(Long id) {
        return this.repoService.delete(id);
    }

    @Override
    public List<Shipment> add(final List<BaseShipment<? extends BaseMaterialLot<?>>> additions) {
        if (additions == null) {
            return null;
        }

        final List<Shipment> entities = this.updateService.getAddEntities(additions);

        for (int i = 0; i < additions.size(); i++) {
            final List<MaterialLot> lots = this.lotService.getAddEntities((List<BaseMaterialLot<?>>) additions.get(i).getLots());
            entities.get(i).setLots(lots);
        }

        return this.repoService.saveAll(entities);
    }

    @Override
    public List<Shipment> put(List<UpdateShipment<? extends UpdateMaterialLot<?>>> updates) {
        if (updates == null) {
            return null;
        }

        final List<Shipment> existing = this.repoService.getByIds(updates);
        final List<Shipment> updated = this.updateService.getPutEntities(existing, updates);

        final int length = Math.max(existing.size(), updates.size());
        for (int i = 0; i < length; i++) {
            final List<MaterialLot> existingLots = i < existing.size() ? existing.get(i).getLots() : null;
            final List<? extends UpdateMaterialLot<?>> updateLots = i < updates.size() ? updates.get(i).getLots() : null;

            final List<MaterialLot> updatedLots = this.lotService.getPutEntities(existingLots, (List<UpdateMaterialLot<?>>) updateLots);

            updated.get(i).setLots(updatedLots);
        }

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<Shipment> patch(List<UpdateShipment<? extends UpdateMaterialLot<?>>> patches) {
        if (patches == null) {
            return null;
        }

        final List<Shipment> existing = this.repoService.getByIds(patches);

        if (existing.size() != patches.size()) {
            final Set<Long> existingIds = existing.stream().map(shipment -> shipment.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patches.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find shipments with Ids: %s", nonExistingIds));
        }

        final List<Shipment> updated = this.updateService.getPatchEntities(existing, patches);

        for (int i = 0; i < existing.size(); i++) {
            final List<MaterialLot> existingLots = existing.get(i).getLots();
            final List<? extends UpdateMaterialLot<?>> updateLots = patches.get(i).getLots();

            final List<MaterialLot> updatedLots = this.lotService.getPatchEntities(existingLots, (List<UpdateMaterialLot<?>>) updateLots);

            updated.get(i).setLots(updatedLots);
        }

        return this.repoService.saveAll(updated);
    }
}
