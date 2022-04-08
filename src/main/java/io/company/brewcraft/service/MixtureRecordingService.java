package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.BaseMixtureRecording;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.UpdateMixtureRecording;

public interface MixtureRecordingService {
    public Page<MixtureRecording> getMixtureRecordings(Set<Long> ids, Set<Long> mixtureIds, Set<Long> brewStageIds, Set<Long> brewIds, int page, int size, SortedSet<String> sort, boolean orderAscending);

    public MixtureRecording getMixtureRecording(Long mixtureRecordingId);

    public List<MixtureRecording> addMixtureRecordings(List<BaseMixtureRecording> mixtureRecordings);

    public List<MixtureRecording> putMixtureRecordings(List<UpdateMixtureRecording> mixtureRecording);

    public List<MixtureRecording> patchMixtureRecordings(List<UpdateMixtureRecording> mixtureRecording);

    public long deleteMixtureRecordings(Set<Long> mixtureRecordingIds);

    public boolean mixtureRecordingExists(Long mixtureRecordingId);

 }

