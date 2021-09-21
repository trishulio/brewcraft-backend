package io.company.brewcraft.repository;

import java.util.Collection;

public interface EnhancedRepository<E, A> {
    void refresh(Collection<E> entities);

    void refreshAccessors(Collection<? extends A> accessors);

}
