package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.MixtureRecording;

public interface MixtureRecordingService {

    public Page<MixtureRecording> getMixtureRecordings(Set<Long> ids, Set<Long> mixtureIds, int page, int size, SortedSet<String> sort, boolean orderAscending);

    public MixtureRecording getMixtureRecording(Long mixtureRecordingId);

    public List<MixtureRecording> addMixtureRecordings(List<MixtureRecording> mixtureRecordings);

    public MixtureRecording addMixtureRecording(MixtureRecording mixtureRecording);

    public MixtureRecording putMixtureRecording(Long mixtureRecordingId, MixtureRecording mixtureRecording);

    public MixtureRecording patchMixtureRecording(Long mixtureRecordingId, MixtureRecording mixtureRecording);

    public void deleteMixtureRecording(Long mixtureRecordingId);

    public boolean mixtureRecordingExists(Long mixtureRecordingId);

 }

