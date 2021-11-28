package io.company.brewcraft.repository;

import java.util.Collection;

public interface IMixtureRefresher<E, A, P> extends Refresher<E, A> {

    void refreshParentMixturesAccessors(Collection<? extends P> accessors);
}
