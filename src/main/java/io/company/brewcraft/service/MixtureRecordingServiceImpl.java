package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseMixtureRecording;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.UpdateMixtureRecording;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class MixtureRecordingServiceImpl extends BaseService implements MixtureRecordingService {
    private static final Logger log = LoggerFactory.getLogger(MixtureRecordingServiceImpl.class);

    private final RepoService<Long, MixtureRecording, MixtureRecordingAccessor> repoService;
    
    private final UpdateService<Long, MixtureRecording, BaseMixtureRecording, UpdateMixtureRecording> updateService;
    
    public MixtureRecordingServiceImpl(RepoService<Long, MixtureRecording, MixtureRecordingAccessor> repoService, UpdateService<Long, MixtureRecording, BaseMixtureRecording, UpdateMixtureRecording> updateService) {
        this.repoService = repoService;
        this.updateService = updateService;
    }

    @Override
    public Page<MixtureRecording> getMixtureRecordings(Set<Long> ids, Set<Long> mixtureIds, Set<Long> brewStageIds, Set<Long> brewIds, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<MixtureRecording> spec = WhereClauseBuilder
                .builder()
                .in(MixtureRecording.FIELD_ID, ids)
                .in(new String[] {MixtureRecording.FIELD_MIXTURE, Mixture.FIELD_ID}, mixtureIds)
                .in(new String[] {MixtureRecording.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_ID}, brewStageIds)
                .in(new String[] {MixtureRecording.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_BREW, Brew.FIELD_ID}, brewIds)
                .build();

            Page<MixtureRecording> mixtureRecordingPage = repoService.getAll(spec, sort, orderAscending, page, size);

            return mixtureRecordingPage;
    }

    @Override
    public MixtureRecording getMixtureRecording(Long mixtureRecordingId) {
        return this.repoService.get(mixtureRecordingId);
    }

    @Override
    public List<MixtureRecording> addMixtureRecordings(List<BaseMixtureRecording> mixtureRecordings) {
        if (mixtureRecordings == null) {
            return null;
        }

        final List<MixtureRecording> entities = this.updateService.getAddEntities(mixtureRecordings);

        return this.repoService.saveAll(entities);
    }
    
    @Override
    public List<MixtureRecording> putMixtureRecordings(List<UpdateMixtureRecording> putMixtureRecordings) {
        if (putMixtureRecordings == null) {
            return null;
        }

        final List<MixtureRecording> existing = this.repoService.getByIds(putMixtureRecordings);
        final List<MixtureRecording> updated = this.updateService.getPutEntities(existing, putMixtureRecordings);

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<MixtureRecording> patchMixtureRecordings(List<UpdateMixtureRecording> patchMixtureRecordings) {
        if (patchMixtureRecordings == null) {
            return null;
        }

        final List<MixtureRecording> existing = this.repoService.getByIds(patchMixtureRecordings);

        if (existing.size() != patchMixtureRecordings.size()) {
            final Set<Long> existingIds = existing.stream().map(mixtureRecording -> mixtureRecording.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patchMixtureRecordings.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find mixture recording with Ids: %s", nonExistingIds));
        }

        final List<MixtureRecording> updated = this.updateService.getPatchEntities(existing, patchMixtureRecordings);

        return this.repoService.saveAll(updated);
    }

    @Override
    public int deleteMixtureRecordings(Set<Long> mixtureRecordingIds) {
        return this.repoService.delete(mixtureRecordingIds);
    }

    @Override
    public boolean mixtureRecordingExists(Long mixtureRecordingId) {
        return this.repoService.exists(mixtureRecordingId);
    }

}
