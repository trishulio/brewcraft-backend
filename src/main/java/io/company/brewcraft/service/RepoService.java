package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Identified;

public interface RepoService<ID, E extends CrudEntity<ID>, A> {
    boolean exists(Set<ID> ids);

    boolean exist(ID id);

    E get(ID id);

    Page<E> getAll(Specification<E> spec, SortedSet<String> sortBy, boolean ascending, int page, int size);

    List<E> getByIds(Collection<? extends Identified<ID>> idProviders);

    List<E> getByAccessorIds(Collection<? extends A> accessors, Function<A, ID> idSupplier);

    List<E> saveAll(List<E> entities);

    int delete(Set<ID> ids);

    void delete(ID id);
}
