package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.repository.EnhancedRepository;
import io.company.brewcraft.repository.ExtendedRepository;

public class CrudRepoService<T extends JpaRepository<E, ID> & JpaSpecificationExecutor<E> & ExtendedRepository<ID> & EnhancedRepository<E, A>, ID, A, E extends Identified<ID>> implements RepoService<ID, E, A> {
    private final T repo;

    public CrudRepoService(T repo) {
        this.repo = repo;
    }

    @Override
    public boolean exists(Set<ID> ids) {
        return this.repo.existsByIds(ids);
    }

    @Override
    public boolean exists(ID id) {
        return this.repo.existsById(id);
    }

    @Override
    public Page<E> getAll(Specification<E> spec, SortedSet<String> sort, boolean orderAscending, int page, int size) {
        final PageRequest pageable = pageRequest(sort, orderAscending, page, size);

        final Page<E> entities = this.repo.findAll(spec, pageable);

        return entities;
    }

    @Override
    public List<E> getAll(Specification<E> spec) {
        final List<E> entities = this.repo.findAll(spec);

        return entities;
    }

    @Override
    public List<E> getByIds(Collection<? extends Identified<ID>> idProviders) {
        if (idProviders == null) {
            return null;
        }

        final Set<ID> ids = idProviders.stream().filter(provider -> provider != null).map(provider -> provider.getId()).filter(id -> id != null).collect(Collectors.toSet());

        return this.repo.findAllById(ids);
    }

    @Override
    public List<E> getByAccessorIds(Collection<? extends A> accessors, Function<A, ? extends Identified<ID>> entityGetter) {
        if (accessors == null) {
            return null;
        }

        final Set<ID> ids = accessors.stream()
                                     .filter(accessor -> accessor != null)
                                     .map(accessor -> entityGetter.apply(accessor))
                                     .filter(identified -> identified != null)
                                     .map(identified -> identified.getId())
                                     .filter(id -> id != null)
                                     .collect(Collectors.toSet());
        return this.repo.findAllById(ids);
    }

    @Override
    public E get(ID id) {
        E po = null;

        final Optional<E> opt = this.repo.findById(id);
        if (opt.isPresent()) {
            po = opt.get();
        }

        return po;
    }

    @Override
    public List<E> saveAll(List<E> entities) {
        this.repo.refresh(entities);
        final Iterable<E> saved = this.repo.saveAll(entities);
        this.repo.flush();

        return (List<E>) saved;
    }

    @Override
    public int delete(Set<ID> ids) {
        return this.repo.deleteByIds(ids);
    }

    @Override
    public int delete(ID id) {
        return this.repo.deleteOneById(id);
    }
}
