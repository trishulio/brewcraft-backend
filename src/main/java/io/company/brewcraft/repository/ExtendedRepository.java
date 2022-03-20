package io.company.brewcraft.repository;

public interface ExtendedRepository<ID> {
    boolean existsByIds(Iterable<ID> ids);

    long deleteByIds(Iterable<ID> ids);

    long deleteOneById(ID id);
}
