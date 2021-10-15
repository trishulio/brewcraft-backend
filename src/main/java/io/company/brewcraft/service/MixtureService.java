package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Mixture;

public interface MixtureService {

    public Page<Mixture> getMixtures(Set<Long> ids, Set<Long> parentMixtureIds, Set<Long> equipmentIds, Set<Long> brewStageIds, Set<Long> brewIds, Set<Long> brewBatchIds, Set<Long> stageStatusIds, Set<Long> stageTaskIds, Set<Long> productIds, int page, int size, SortedSet<String> sort, boolean orderAscending);

    public Mixture getMixture(Long mixtureId);

    public Mixture addMixture(Mixture mixture);

    public Mixture putMixture(Long mixtureId, Mixture mixture);

    public Mixture patchMixture(Long mixtureId, Mixture mixture);

    public void deleteMixture(Long mixtureId);

    public boolean mixtureExists(Long mixtureId);

 }

