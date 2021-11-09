package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Identified;

public interface RepoService<ID, E extends Identified<ID>, A> {
    boolean exists(Set<ID> ids);

    boolean exists(ID id);

    E get(ID id);

    Page<E> getAll(Specification<E> spec, SortedSet<String> sort, boolean orderAscending, int page, int size);

    List<E> getByIds(Collection<? extends Identified<ID>> idProviders);

    List<E> getByAccessorIds(Collection<? extends A> accessors, Function<A, ? extends Identified<ID>> entityGetter);

    List<E> saveAll(List<E> entities);

    int delete(Set<ID> ids);

    int delete(ID id);
}
