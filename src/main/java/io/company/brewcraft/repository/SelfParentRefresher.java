package io.company.brewcraft.repository;

import java.util.Collection;

public interface SelfParentRefresher<P> {

    void refreshParentAccessors(Collection<? extends P> accessors);

}
