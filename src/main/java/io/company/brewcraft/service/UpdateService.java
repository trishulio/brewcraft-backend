package io.company.brewcraft.service;

import java.util.List;

public interface UpdateService<ID, E extends CrudEntity<ID>, BE, UE extends UpdatableEntity<ID>> {
    List<E> getAddEntites(List<BE> additions);

    List<E> getPutEntities(List<E> existingItems, List<UE> updates);

    List<E> getPatchEntities(List<E> existingItems, List<UE> patches);
}
