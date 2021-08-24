package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;

public interface CrudService<ID, E extends CrudEntity<ID>, BE, UE extends UpdatableEntity<ID>> {

    boolean exists(Set<ID> ids);

    boolean exist(ID id);

    int delete(Set<ID> ids);

    void delete(ID id);

    E get(ID id);

    List<E> add(List<BE> additions);

    List<E> put(List<UE> updates);

    List<E> patch(List<UE> updates);
}
