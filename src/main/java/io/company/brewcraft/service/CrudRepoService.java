package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.repository.EnhancedRepository;

public class CrudRepoService<T extends JpaRepository<E, ID> & JpaSpecificationExecutor<E> & EnhancedRepository<E, A>, ID, A, E extends CrudEntity<ID>> implements RepoService<ID, E> {
    private final T repo;

    public CrudRepoService(T repo) {
        this.repo = repo;
    }

    @Override
    public boolean exists(Set<ID> ids) {
//        return this.repo.existsByIds(ids);
        return false;
    }

    @Override
    public boolean exist(ID id) {
        return this.repo.existsById(id);
    }

    @Override
    public Page<E> getAll(Specification<E> spec, SortedSet<String> sortBy, boolean ascending, int page, int size) {
        final PageRequest pageable = pageRequest(sortBy, ascending, page, size);

        final Page<E> entities = this.repo.findAll(spec, pageable);

        return entities;
    }

    @Override
    public List<E> getByIds(Collection<? extends Identified<ID>> idProviders) {
        final Set<ID> ids = idProviders.stream().map(provider -> provider.getId()).collect(Collectors.toSet());
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
//        return this.repo.deleteByIds(ids);
        return -1;
    }

    @Override
    public void delete(ID id) {
        this.repo.deleteById(id);
    }

}
