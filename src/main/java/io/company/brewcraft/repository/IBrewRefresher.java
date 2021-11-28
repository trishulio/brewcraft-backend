package io.company.brewcraft.repository;

import java.util.Collection;

public interface IBrewRefresher<E, A, P> extends Refresher<E,A> {

    void refreshParentBrewAccessors(Collection<? extends P> accessors);

}
