package io.company.brewcraft.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

public class MaterialLotService extends BaseService {

    private UtilityProvider utilProvider;

    public MaterialLotService(UtilityProvider utilProvider) {
        this.utilProvider = utilProvider;
    }

    public List<MaterialLot> getAddLots(List<? extends BaseMaterialLot<?>> additionLots) {
        List<MaterialLot> targetLots = null;
        if (additionLots != null) {
            targetLots = additionLots.stream().map(addition -> {
                MaterialLot lot = new MaterialLot();
                lot.override(addition, getPropertyNames(BaseMaterialLot.class));
                return lot;
            }).collect(Collectors.toList());
        }

        return targetLots;
    }

    public List<MaterialLot> getPutLots(List<MaterialLot> existingLots, List<? extends UpdateMaterialLot<?>> updateLots) {
        Validator validator = this.utilProvider.getValidator();

        if (updateLots == null) {
            return null;
        }

        int maxPossibleTargetSize = existingLots == null ? 0 : existingLots.size() + updateLots.size();
        List<MaterialLot> targetLots = new ArrayList<>(maxPossibleTargetSize);

        // Separating the put payloads into additions (without Id param) and updates
        // (with Ids that match and existing lot)
        List<UpdateMaterialLot<?>> updates = new ArrayList<UpdateMaterialLot<?>>(updateLots.size());
        for (UpdateMaterialLot<?> update : updateLots) {
            if (update.getId() != null) {
                updates.add(update);
            } else {
                MaterialLot lot = new MaterialLot();
                lot.override(update, getPropertyNames(BaseMaterialLot.class));
                targetLots.add(lot);
            }
        }

        existingLots = existingLots != null ? existingLots : new ArrayList<>(0);
        Map<Long, MaterialLot> existingLotsLookup = existingLots.stream().collect(Collectors.toMap(lot -> lot.getId(), lot -> lot));

        updates.forEach(update -> {
            MaterialLot lot = existingLotsLookup.get(update.getId());
            if (validator.rule(lot != null, "No existing lot found with Id: %s. For adding a new lot, don't include Id in the payload.", update.getId())) {
                lot.optimisicLockCheck(update);
                lot.override(update, getPropertyNames(UpdateMaterialLot.class));
                targetLots.add(lot);
            }
        });

        validator.raiseErrors();
        return targetLots;
    }

    public List<MaterialLot> getPatchLots(List<MaterialLot> existingLots, List<? extends UpdateMaterialLot<?>> updateLots) {
        Validator validator = this.utilProvider.getValidator();

        existingLots = existingLots == null ? new ArrayList<>() : existingLots;
        List<MaterialLot> targetLots = existingLots.stream().collect(Collectors.toList());

        if (updateLots != null) {
            Map<Long, MaterialLot> existingLotsLookup = existingLots.stream().collect(Collectors.toMap(lot -> lot.getId(), lot -> lot));

            updateLots.forEach(update -> {
                MaterialLot lot = existingLotsLookup.get(update.getId());
                if (validator.rule(lot != null, "No existing lot found with Id: %s. Use the put method to add new payload lot. Patch can only perform patches on existing lots.", update.getId())) {
                    lot.optimisicLockCheck(update);
                    lot.outerJoin(update, getPropertyNames(UpdateMaterialLot.class));
                }
            });
        }

        validator.raiseErrors();
        return targetLots;
    }
}
